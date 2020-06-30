package apps.somniac.fightme.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import apps.somniac.fightme.converters.EntityToDto;
import apps.somniac.fightme.dtos.FaceIdDto;
import apps.somniac.fightme.dtos.UserDto;
import apps.somniac.fightme.entities.UserEntity;
import apps.somniac.fightme.exceptions.PersonIdNoContentException;
import apps.somniac.fightme.exceptions.UserNoContentException;
import apps.somniac.fightme.exceptions.generic.NoContentException;
import apps.somniac.fightme.exceptions.messages.DataErrorMessages;
import apps.somniac.fightme.repositories.UserRepository;
import apps.somniac.fightme.services.FaceIdService;

@Service
public class FaceIdServiceImpl implements FaceIdService {
	
	private Logger logger = LoggerFactory.getLogger(FaceIdServiceImpl.class);
	
	private static final String SUBSCRIPTION_KEY = "b95ebdc8930e4e5da6b2d902b1f0bf4e";
	
    private static final String URI_DETECT = "https://proyectoformativo2020.cognitiveservices.azure.com/face/v1.0/detect";
    private static final String URI_VERIFY = "https://proyectoformativo2020.cognitiveservices.azure.com/face/v1.0/verify";
    private static final String URI_CREATE_PERSON = "https://proyectoformativo2020.cognitiveservices.azure.com/face/v1.0/persongroups/users/persons";
    private static final String URI_DELETE_PERSON = "https://proyectoformativo2020.cognitiveservices.azure.com/face/v1.0/persongroups/users/persons/";
    private static final String URI_ADD_FACE = "https://proyectoformativo2020.cognitiveservices.azure.com/face/v1.0/persongroups/users/persons/";
    private static final String URI_TRAIN = "https://proyectoformativo2020.cognitiveservices.azure.com/face/v1.0/persongroups/users/train";
    private static final String URI_LIST = "https://proyectoformativo2020.cognitiveservices.azure.com/face/v1.0/persongroups/users/persons";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EntityToDto etd;
	
	// ----- createPersonId: Registra un personId con el nombre de usuario correspondiente al ID pasado por parámetro ----------------------------------------- //
	@Override
	@Transactional
	public UserDto createPersonId(Long id) {
		
		UserEntity u = userRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.USER_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
		});

		HttpClient httpclient = HttpClientBuilder.create().build();

        try {
        	
        	URIBuilder builder = new URIBuilder(URI_CREATE_PERSON);

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);
                        
            String createPerson = "{\r\n" + 
    				"  \"name\": \"" + u.getUsername() + "\"\r\n" + 
    				"}";
                        
            // Request body.
            StringEntity reqEntity = new StringEntity(createPerson);
            request.setEntity(reqEntity);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            
        	String jsonString = EntityUtils.toString(entity).trim();

			JSONObject jsonObject = new JSONObject(jsonString);
						
			u.setFaceId(jsonObject.getString("personId"));
	        userRepository.save(u);
			
			return etd.getUserWithoutPassword(u);
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoContentException(DataErrorMessages.GENERIC_NO_CONTENT);
        }
		
	}
	
	// ----- deletePersonId: Borra el personId asociado a un usuario en concreto ------------------------------------------------------------------------------ //
	@Override
	@Transactional
	public UserDto deletePersonId(Long id) {
					
		UserEntity u = userRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.USER_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
		});
		
		UserEntity temp = new UserEntity();

		HttpClient httpclient = HttpClientBuilder.create().build();

        try {
        	
        	URIBuilder builder = new URIBuilder(URI_DELETE_PERSON + u.getFaceId());

            URI uri = builder.build();
            HttpDelete request = new HttpDelete(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            
            if (response.getStatusLine().getStatusCode() == 404) {
    			logger.warn(DataErrorMessages.PERSONID_NO_CONTENT);
    			throw new PersonIdNoContentException(DataErrorMessages.PERSONID_NO_CONTENT);
            }
            
            if (response.getStatusLine().getStatusCode() == 200) {
            	u.setFaceId(temp.getFaceId());
            	userRepository.save(u);
            }
            
            return etd.getUserWithoutPassword(u);

        } catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoContentException(DataErrorMessages.GENERIC_NO_CONTENT);
        }
		
	}
	
	// ----- fillPersonId: Añade información obtenida de diferentes fotografías a un personId específico ------------------------------------------------------ //
	@Override
	@Transactional(readOnly = true)
	public void fillPersonId(MultipartFile file, Long id) {
		
		UserDto user = etd.getUser(userRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.USER_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
		}));
				
		HttpClient httpclient = HttpClientBuilder.create().build();

        try {

        	URIBuilder builder = new URIBuilder(URI_ADD_FACE + user.getFaceId() + "/persistedfaces");

        	URI uri = builder.build();
        	HttpPost request = new HttpPost(uri);

			request.setHeader("Content-Type", "application/octet-stream");
			request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

			ByteArrayEntity req = new ByteArrayEntity(file.getBytes());
			
			request.setEntity(req);
			
			HttpResponse response = httpclient.execute(request);
			
			if (response.getStatusLine().getStatusCode() == 400)
				throw new Exception(DataErrorMessages.IMAGE_NO_CONTENT);

			trainFacialRecognition();
			
        } catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoContentException(e.getMessage());
        }
		
	}
	
	// ----- trainFacialRecognition: Entrena el reconocimiento facial ----------------------------------------------------------------------------------------- //
	@Override
	public void trainFacialRecognition() {
		
		HttpClient httpclient = HttpClientBuilder.create().build();

        try {
        	
        	URIBuilder builder = new URIBuilder(URI_TRAIN);

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);
            
            httpclient.execute(request);
            
        } catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoContentException(DataErrorMessages.GENERIC_NO_CONTENT);
        }
		
	}
	
	// ----- listPersonId: Devuelve una lista con todos los usuarios registrados en el sistema de FaceId ------------------------------------------------------ //
    @Override
	public FaceIdDto[] listPersonId() {
    	    	
    	HttpClient httpclient = HttpClientBuilder.create().build();

        try {
        	
        	URIBuilder builder = new URIBuilder(URI_LIST);

            URI uri = builder.build();
            HttpGet request = new HttpGet(uri);

            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);
            
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            
            String jsonString = EntityUtils.toString(entity).trim();                      
            Gson gson = new Gson();
            FaceIdDto lista[] = gson.fromJson(jsonString, FaceIdDto[].class);
            
            return lista;
            
        } catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoContentException(DataErrorMessages.GENERIC_NO_CONTENT);
        }
    			
	}
	
	// ----- facialRecognition: Comprueba si una foto pasada por parámetro pertenece a un personId específico ------------------------------------------------- //
    @Override
    @Transactional(readOnly = true)
	public FaceIdDto facialRecognition(String username, String image) {
    	
    	image = image.split(",")[1];
    	
    	UserEntity u = userRepository.findByUsername(username).orElseThrow(() -> {
			logger.warn(DataErrorMessages.USER_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
		});
    	
    	FaceIdDto face = new FaceIdDto();
    	
    	String faceId = pictureToFaceId(image);
    	
    	if (faceId.length() == 0)
    		return face;
    	    	
    	face.setPersonId(verify(u.getIdUser(), faceId));
    			
    	return face;
		
	}
	
	private String pictureToFaceId(String image) {
				
		HttpClient httpclient = HttpClientBuilder.create().build();
		
		try {
			
			URIBuilder builder = new URIBuilder(URI_DETECT);

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/octet-stream");
			request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

            // Request body.
			byte[] imageByte = Base64.getDecoder().decode(image);
			ByteArrayEntity req = new ByteArrayEntity(imageByte);
			
			request.setEntity(req);
			
			HttpResponse response = httpclient.execute(request);
			HttpEntity entity = response.getEntity();
						
            if (entity != null) {
            	
                String jsonString = EntityUtils.toString(entity).trim();
                
                if (jsonString.length() <= 2)
                	return "";

                JSONArray test = new JSONArray(jsonString);                
                
                JSONObject testJson = new JSONObject(test.get(0).toString());
                
                return testJson.getString("faceId");
                
            } else {
            	logger.error(DataErrorMessages.GENERIC_NO_CONTENT);
            	throw new NoContentException(DataErrorMessages.GENERIC_NO_CONTENT);
            }
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoContentException(DataErrorMessages.GENERIC_NO_CONTENT);
		}
		
	}
	
	private String verify(Long id, String faceId) {
		
		UserDto user = etd.getUser(userRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.USER_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
		}));
		
		String personId = user.getFaceId();
		
		HttpClient httpclient = HttpClientBuilder.create().build();

        try {
        	
        	URIBuilder builder = new URIBuilder(URI_VERIFY);

            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);
                        
            String faceToPerson = "{\r\n" + 
            		"  \"faceId\": \"" + faceId + "\",\r\n" + 
            		"  \"personId\": \"" + personId + "\",\r\n" + 
            		"  \"personGroupId\": \"users\"\r\n" + 
            		"}";
                        
            // Request body.
            StringEntity reqEntity = new StringEntity(faceToPerson);
            request.setEntity(reqEntity);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            
        	String jsonString = EntityUtils.toString(entity).trim();
        	
			JSONObject jsonObject = new JSONObject(jsonString);
			
			double confidence = jsonObject.getDouble("confidence");
			boolean isIdentical = jsonObject.getBoolean("isIdentical");
			
			logger.info("CONFIDENCE: " + confidence);
			logger.info("IS IDENTICAL: " + isIdentical);
			
			if (isIdentical)
				return user.getFaceId();
			else
				return null;
            
        } catch (Exception e) {
			logger.error(e.getMessage());
			throw new NoContentException(DataErrorMessages.GENERIC_NO_CONTENT);
        }
		        
	}
	
}

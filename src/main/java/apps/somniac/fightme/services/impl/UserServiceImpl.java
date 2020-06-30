package apps.somniac.fightme.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import apps.somniac.fightme.converters.DtoToEntity;
import apps.somniac.fightme.converters.EntityToDto;
import apps.somniac.fightme.dtos.UserDto;
import apps.somniac.fightme.entities.RolEntity;
import apps.somniac.fightme.entities.UserEntity;
import apps.somniac.fightme.exceptions.EmailAlreadyExistsException;
import apps.somniac.fightme.exceptions.UserNoContentException;
import apps.somniac.fightme.exceptions.UsernameAlreadyExistsException;
import apps.somniac.fightme.exceptions.messages.DataErrorMessages;
import apps.somniac.fightme.repositories.RolRepository;
import apps.somniac.fightme.repositories.UserRepository;
import apps.somniac.fightme.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	EntityToDto etd;

	@Autowired
	DtoToEntity dte;

	// addUser: Añade un nuevo usuario a la BBDD
	@Override
	@Transactional
	public void addUser(UserDto user) {
		
		if (userRepository.findByUsername(user.getUserName()).isPresent()) {
			logger.warn(DataErrorMessages.USERNAME_ALREADY_BEEN_USED);
			throw new UsernameAlreadyExistsException(DataErrorMessages.USERNAME_ALREADY_BEEN_USED);
		}
		
		if (userRepository.findByMail(user.getMail()).isPresent()) {
			logger.warn(DataErrorMessages.EMAIL_ALREADY_BEEN_USED);
			throw new EmailAlreadyExistsException(DataErrorMessages.EMAIL_ALREADY_BEEN_USED);
		}

		UserEntity u = dte.getUser(user);

		RolEntity r = rolRepository.findByName(user.getRol().getName().toString()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.ROLE_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.ROLE_NO_CONTENT);
		});

		r.getUsers().add(u);

		userRepository.save(u);
				
	}

	// modifyUser: Modifica los datos de un usuario, pasando su id y los nuevos datos por parámetro
	@Override
	@Transactional
	public UserDto modifyUser(Long id, UserDto user) {

		UserEntity u = userRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.USER_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
		});
		
		if (!user.getUserName().equalsIgnoreCase(u.getUsername()) && userRepository.findByUsername(user.getUserName()).isPresent()) {
			logger.warn(DataErrorMessages.USERNAME_ALREADY_BEEN_USED);
			throw new UsernameAlreadyExistsException(DataErrorMessages.USERNAME_ALREADY_BEEN_USED);
		}
		
		if (!user.getMail().equalsIgnoreCase(u.getMail()) && userRepository.findByMail(user.getMail()).isPresent()) {
			logger.warn(DataErrorMessages.EMAIL_ALREADY_BEEN_USED);
			throw new EmailAlreadyExistsException(DataErrorMessages.EMAIL_ALREADY_BEEN_USED);
		}

		u.setUsername(user.getUserName());
		u.setBirthdate(user.getBirthdate());
		u.setCountry(user.getCountry());
		u.setMail(user.getMail());

		for (RolEntity r : u.getRoles())
			r.getUsers().remove(u);

		RolEntity r = rolRepository.findByName(user.getRol().getName().toString()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.ROLE_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.ROLE_NO_CONTENT);
		});

		r.getUsers().add(u);

		userRepository.save(u);

		return user;

	}
	
	// modifyPassword: Actualiza la contraseña de un usuario
	@Override
	@Transactional
	public UserDto modifyPassword(Long id, UserDto user) {

		UserEntity u = userRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.USER_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
		});
		
		if (!user.getUserName().equalsIgnoreCase(u.getUsername()) && userRepository.findByUsername(user.getUserName()).isPresent()) {
			logger.warn(DataErrorMessages.USERNAME_ALREADY_BEEN_USED);
			throw new UsernameAlreadyExistsException(DataErrorMessages.USERNAME_ALREADY_BEEN_USED);
		}
		
		if (!user.getMail().equalsIgnoreCase(u.getMail()) && userRepository.findByMail(user.getMail()).isPresent()) {
			logger.warn(DataErrorMessages.EMAIL_ALREADY_BEEN_USED);
			throw new EmailAlreadyExistsException(DataErrorMessages.EMAIL_ALREADY_BEEN_USED);
		}

		u.setUsername(user.getUserName());
		u.setBirthdate(user.getBirthdate());
		u.setPassword(encoder.encode(user.getPassword()));
		u.setCountry(user.getCountry());
		u.setMail(user.getMail());

		for (RolEntity r : u.getRoles())
			r.getUsers().remove(u);

		RolEntity r = rolRepository.findByName(user.getRol().getName().toString()).orElseThrow(() -> {
			logger.warn(DataErrorMessages.ROLE_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.ROLE_NO_CONTENT);
		});

		r.getUsers().add(u);

		userRepository.save(u);

		return user;

	}

	// deleteUser: Elimina un usuario de la BBDD
	@Override
	@Transactional
	public void deleteUser(Long id) {

		UserEntity u = userRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.USER_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
		});

		for (RolEntity r : u.getRoles())
			r.getUsers().remove(u);

		userRepository.delete(u);

	}

	// getUsers: Muestra los datos de todos los usuarios registrados en la BBDD
	@Override
	@Transactional(readOnly = true)
	public List<UserDto> getUsers() {

		List<UserEntity> userEntities = userRepository.findAll();
		List<UserDto> userDTOs = new ArrayList<>();

		for (UserEntity u : userEntities)
			userDTOs.add(etd.getUser(u));
		return userDTOs;

	}

	// getUser: Muestra los datos de un único usuario (buscando por id)
	@Override
	public UserDto getUser(Long id) {

		return etd.getUserWithoutPassword(userRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.USER_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
		}));

	}

	// getUserByUsername: Muestra los datos de un único usuario (buscando por username)
	@Override
	public UserDto getUserByUserName(String userName) {
		return etd.getUser(userRepository.findByUsername(userName).orElseThrow(() -> {
			logger.warn(DataErrorMessages.USER_NO_CONTENT);
			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
		}));
	}
	
	// getUsersPerPage: Muestra todos los usuarios registrados en la BBDD de manera paginada
	@Override
	@Transactional(readOnly = true)
	public Page<UserDto> getUsersPerPage(Integer page) {
		
		Page<UserEntity> paginator = userRepository.findAll(PageRequest.of(page, 5));
		
		Page<UserDto> paginatorDto = paginator.map(new Function<UserEntity, UserDto>() {
			@Override
			public UserDto apply(UserEntity d) {
				return etd.getUser(d);
			}
		});

		return paginatorDto;
		
	}

	@Override
	public List<UserDto> getUserByName(String userName) {
		
		System.out.println("Entra en la función: " + userName);
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
		
		List<UserEntity> listaCompleta = userRepository.findAll();
		List<UserDto> listaFinalDtos = new ArrayList<>();
		
		for (UserEntity u : listaCompleta) {
			
			if (u.getUsername().toLowerCase().indexOf(userName.toLowerCase()) != -1)
				listaFinalDtos.add(etd.getUser(u));
			
		}
		
		System.out.println("Tamaño lista coincidencias: " + listaFinalDtos.size());
		
		return listaFinalDtos;
		
	}

//	@Override
//	public void uploadImage(MultipartFile file, Long idUser) {
//		
//		UserEntity u = userRepository.findById(idUser).orElseThrow(() -> {
//			logger.warn(DataErrorMessages.USER_NO_CONTENT);
//			throw new UserNoContentException(DataErrorMessages.USER_NO_CONTENT);
//		});
//		
//		if(!file.isEmpty()) {
//			String fileName = "user=" + idUser + "_" + file.getOriginalFilename().replace(" ", "_");
//			Path filePath = Paths.get("resources/usersImages").resolve(fileName).toAbsolutePath();
//			try {
//				Files.copy(file.getInputStream(), filePath);
//			} catch (IOException e) {
//				logger.warn(DataErrorMessages.FAIL_TO_UPLOAD_FILE);
//				throw new UserNoContentException(DataErrorMessages.FAIL_TO_UPLOAD_FILE);
//			}
//			
//			u.setImage(fileName);
//			userRepository.save(u); //Revisar
//			System.out.println("Imagen: " + fileName + " almacenada en la ruta " + filePath);
//			System.out.println("-------------------------------------------------------------------------------------------");
//		}
//		
//	}

}

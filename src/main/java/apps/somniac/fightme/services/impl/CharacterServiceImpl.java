package apps.somniac.fightme.services.impl;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import apps.somniac.fightme.converters.DtoToEntity;
import apps.somniac.fightme.converters.EntityToDto;
import apps.somniac.fightme.dtos.CharacterDto;
import apps.somniac.fightme.entities.CharacterEntity;
import apps.somniac.fightme.exceptions.CharacterNameAlreadyExistsException;
import apps.somniac.fightme.exceptions.CharacterNoContentException;
import apps.somniac.fightme.exceptions.messages.DataErrorMessages;
import apps.somniac.fightme.repositories.CharacterRepository;
import apps.somniac.fightme.services.CharacterService;

@Service
public class CharacterServiceImpl implements CharacterService {

	private Logger logger = LoggerFactory.getLogger(CharacterServiceImpl.class);

	@Autowired
	private CharacterRepository characterRepository;

	@Autowired
	EntityToDto etd;

	@Autowired
	DtoToEntity dte;

	// addCharacter: Añade un nuevo personaje a la BBDD
	@Override
	@Transactional
	public void addCharacter(CharacterDto character) {
		
		if (characterRepository.findByName(character.getName()).isPresent()) {
			logger.warn(DataErrorMessages.CHARACTERNAME_ALREADY_BEEN_USED);
			throw new CharacterNameAlreadyExistsException(DataErrorMessages.CHARACTERNAME_ALREADY_BEEN_USED);
		}
		
		CharacterEntity c = dte.getCharacter(character);
		c.setVictories(0);
		c.setDefeats(0);
		characterRepository.save(c);
	}

	// getCharacter: Muestra los datos de un único personaje
	@Override
	public CharacterDto getCharacter(Long id) {

		return etd.getCharacter(characterRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CHARACTER_NO_CONTENT);
			throw new CharacterNoContentException(DataErrorMessages.CHARACTER_NO_CONTENT);
		}));

	}

	// getCharacters: Muestra los datos de todos los personajes registrados en la BBDD
	@Override
	@Transactional(readOnly = true)
	public List<CharacterDto> getCharacters() {

		List<CharacterDto> dtoList = new ArrayList<>();

		for (CharacterEntity c : characterRepository.findAll()) {
			dtoList.add(etd.getCharacter(c));
		}

		return dtoList;

	}

	// modifyCharacter: Modifica los datos de un personaje, pasando su id y los nuevos datos por parámetro
	@Override
	@Transactional
	public CharacterDto modifyCharacter(Long id, CharacterDto character) {

		CharacterEntity c = characterRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CHARACTER_NO_CONTENT);
			throw new CharacterNoContentException(DataErrorMessages.CHARACTER_NO_CONTENT);
		});
		
		if (!character.getName().equalsIgnoreCase(c.getName()) && characterRepository.findByName(character.getName()).isPresent()) {
			logger.warn(DataErrorMessages.CHARACTERNAME_ALREADY_BEEN_USED);
			throw new CharacterNameAlreadyExistsException(DataErrorMessages.CHARACTERNAME_ALREADY_BEEN_USED);
		}

		c = dte.getCharacter(character);
		c.setIdCharacter(id);
		characterRepository.save(c);

		return character;

	}

	// deleteCharacter: Elimina un personaje de la BBDD
	@Override
	@Transactional
	public void deleteCharacter(Long id) {

		CharacterEntity c = characterRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.CHARACTER_NO_CONTENT);
			throw new CharacterNoContentException(DataErrorMessages.CHARACTER_NO_CONTENT);
		});

		// TODO Lógica de borrado

		characterRepository.delete(c);

	}
	
	// imgViewCharacter: Devuelve la imagen correspondiente a cada personaje.
	@Override
	@Transactional
	public Resource imgViewCharacter(String img) {
		
		Resource res = null;
		
		try {
			res = new UrlResource(Paths.get("resources/imgs/characters").resolve(img).toAbsolutePath().toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		if(!res.exists() && !res.isReadable()) {
			try {
				res = new UrlResource(Paths.get("src/main/resources/static/imgs").resolve("peasant.png").toAbsolutePath().toUri());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			logger.warn(DataErrorMessages.IMG_NO_CONTENT);
		}
		
		return res;
		
	}
	
	// getCharactersPerPage: Muestra todos los personajes registrados en la BBDD de manera paginada
	@Override
	@Transactional(readOnly = true)
	public Page<CharacterDto> getCharactersPerPage(Integer page) {
		
		Page<CharacterEntity> paginator = characterRepository.findAll(PageRequest.of(page, 5));
		
		Page<CharacterDto> paginatorDto = paginator.map(new Function<CharacterEntity, CharacterDto>() {
			@Override
			public CharacterDto apply(CharacterEntity d) {
				return etd.getCharacter(d);
			}
		});

		return paginatorDto;
		
	}

	@Override
	public List<CharacterDto> getCharacterByName(String name) {
		System.out.println("Entra en la función: " + name);
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
		List<CharacterEntity> listaCompleta = characterRepository.findAll();
		List<CharacterDto> listaFinalDtos = new ArrayList<>();
		
		for (CharacterEntity c : listaCompleta) {
			if (c.getName().toLowerCase().indexOf(name.toLowerCase()) != -1) {
				listaFinalDtos.add(etd.getCharacter(c));
			}
		}
		
		System.out.println("Tamaño lista coincidencias: " + listaFinalDtos.size());
		return listaFinalDtos;
	}

}

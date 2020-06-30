package apps.somniac.fightme.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apps.somniac.fightme.converters.DtoToEntity;
import apps.somniac.fightme.converters.EntityToDto;
import apps.somniac.fightme.dtos.CharacterRolDto;
import apps.somniac.fightme.entities.CharacterRolEntity;
import apps.somniac.fightme.exceptions.CharacterRolNoContentException;
import apps.somniac.fightme.exceptions.messages.DataErrorMessages;
import apps.somniac.fightme.repositories.CharacterRolRepository;
import apps.somniac.fightme.services.CharacterRolService;

@Service
public class CharacterRolServiceImpl implements CharacterRolService {

	@Autowired
	CharacterRolRepository chRepo;

	@Autowired
	EntityToDto etd;

	@Autowired
	DtoToEntity dte;

	@Override
	public CharacterRolDto getCharacterRol(Long id) {
		return etd.getCharacterRol(chRepo.findById(id)
				.orElseThrow(() -> new CharacterRolNoContentException(DataErrorMessages.CHARACTER_ROL_NO_CONTENT)));
	}

	@Override
	public List<CharacterRolDto> getCharactersRol() {
		List<CharacterRolDto> chDto = new ArrayList<CharacterRolDto>();
		for (CharacterRolEntity chRol : chRepo.findAll()) {
			chDto.add(etd.getCharacterRol(chRol));
		}
		return chDto;
	}

	@Override
	public void addCharacterRol(CharacterRolDto characterRolDto) {
		chRepo.save(dte.getCharacterRol(characterRolDto));
	}

	@Override
	public CharacterRolDto modifyCharacterRol(CharacterRolDto characterRolDto, Long id) {
		CharacterRolEntity chRol = chRepo.findById(id)
				.orElseThrow(() -> new CharacterRolNoContentException(DataErrorMessages.CHARACTER_ROL_NO_CONTENT));
		chRol = dte.getCharacterRol(characterRolDto);
		chRol.setRolId(id);
		chRepo.save(chRol);
		return characterRolDto;
	}

	@Override
	public void deleteCharacterRol(Long id) {
		chRepo.delete(chRepo.findById(id)
				.orElseThrow(() -> new CharacterRolNoContentException(DataErrorMessages.CHARACTER_ROL_NO_CONTENT)));
	}

}

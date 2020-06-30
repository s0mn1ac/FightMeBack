package apps.somniac.fightme.services;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import apps.somniac.fightme.dtos.CharacterDto;

@Service
public interface CharacterService {
	
	public void addCharacter(CharacterDto character);
	public CharacterDto getCharacter(Long id);
	public List<CharacterDto>getCharacters();
	public CharacterDto modifyCharacter(Long id, CharacterDto character);
	public void deleteCharacter(Long id);
	public Resource imgViewCharacter(String img);
	public Page<CharacterDto> getCharactersPerPage(Integer page);
	public List<CharacterDto>getCharacterByName(String name);

}

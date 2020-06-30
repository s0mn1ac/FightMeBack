package apps.somniac.fightme.services;

import java.util.List;

import apps.somniac.fightme.dtos.CharacterRolDto;

public interface CharacterRolService {
	
	public CharacterRolDto getCharacterRol(Long id);
	public List<CharacterRolDto> getCharactersRol();
	public void addCharacterRol(CharacterRolDto characterRolDto); 
	public CharacterRolDto modifyCharacterRol(CharacterRolDto characterRolDto, Long id);
	public void deleteCharacterRol(Long id);

}

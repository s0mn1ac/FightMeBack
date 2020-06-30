package apps.somniac.fightme.converters;

import java.util.List;

import apps.somniac.fightme.dtos.CharacterDto;
import apps.somniac.fightme.dtos.CharacterRolDto;
import apps.somniac.fightme.dtos.RolDto;
import apps.somniac.fightme.dtos.SkillDto;
import apps.somniac.fightme.dtos.UserDto;
import apps.somniac.fightme.entities.CharacterEntity;
import apps.somniac.fightme.entities.CharacterRolEntity;
import apps.somniac.fightme.entities.RolEntity;
import apps.somniac.fightme.entities.SkillEntity;
import apps.somniac.fightme.entities.UserEntity;

public interface EntityToDto {

	public UserDto getUser(UserEntity userEntity);
	public UserDto getUserWithoutPassword(UserEntity userEntity);
	public CharacterDto getCharacter(CharacterEntity characterEntity);
	public List<RolDto> getRol(List<RolEntity> rolEntity);
	public CharacterRolDto getCharacterRol(CharacterRolEntity characterRolEntity);
	public SkillDto getSkill(SkillEntity skill);
	public List<SkillDto> getSkillList(List<SkillEntity> entityList);
}

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

public interface DtoToEntity {

	public UserEntity getUser(UserDto userDto);
	public CharacterEntity getCharacter(CharacterDto characterDto);
	public List<RolEntity> getRol(RolDto rolDto);
	public CharacterRolEntity getCharacterRol(CharacterRolDto characterRolDto);
	public SkillEntity getSkill(SkillDto skillDto);
	public List<SkillEntity> getSkillList(List<SkillDto> dtoList);

}

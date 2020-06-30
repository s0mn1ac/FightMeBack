package apps.somniac.fightme.converters.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import apps.somniac.fightme.converters.EntityToDto;
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
import apps.somniac.fightme.enums.Enums;
import apps.somniac.fightme.repositories.CharacterRolRepository;

@Service
public class EntityToDtoImpl implements EntityToDto {

	@Autowired
	CharacterRolRepository characterRolRepository;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Override
	public UserDto getUser(UserEntity userEntity) {
		UserDto userDto = new UserDto();
		userDto.setIdUser(userEntity.getIdUser());
		userDto.setUserName(userEntity.getUsername());
		userDto.setPassword(encoder.encode(userEntity.getPassword()));
		userDto.setMail(userEntity.getMail());
		userDto.setBirthdate(userEntity.getBirthdate());
		userDto.setCountry(userEntity.getCountry());
		userDto.setRol(getRol(userEntity.getRoles()).get(0));
		userDto.setFaceId(userEntity.getFaceId());
		//userDto.setImage(userEntity.getImage());
		return userDto;
	}
	
	@Override
	public UserDto getUserWithoutPassword(UserEntity userEntity) {
		UserDto userDto = new UserDto();
		userDto.setIdUser(userEntity.getIdUser());
		userDto.setUserName(userEntity.getUsername());
		userDto.setMail(userEntity.getMail());
		userDto.setBirthdate(userEntity.getBirthdate());
		userDto.setCountry(userEntity.getCountry());
		userDto.setRol(getRol(userEntity.getRoles()).get(0));
		userDto.setFaceId(userEntity.getFaceId());
		//userDto.setImage(userEntity.getImage());
		return userDto;
	}

	@Override
	public CharacterDto getCharacter(CharacterEntity characterEntity) {

		CharacterDto c = new CharacterDto();

		c.setLvl(characterEntity.getLvl());
		c.setExperience(characterEntity.getExperience());
		c.setHp(characterEntity.getHp());
		c.setIdCharacter(characterEntity.getIdCharacter());
		c.setIntelligence(characterEntity.getIntelligence());
		c.setMagic(characterEntity.getMagic());
		c.setName(characterEntity.getName());
		c.setSpeed(characterEntity.getSpeed());
		c.setStrength(characterEntity.getStrength());
		c.setImg(characterEntity.getImg());
		c.setVictories(characterEntity.getVictories());
		c.setDefeats(characterEntity.getDefeats());
		if (characterEntity.getUser() != null) {
			c.setUser(getUser(characterEntity.getUser()));
		}
		if (characterEntity.getCharacterRol() != null) {
			c.setCharacterRol(getCharacterRol(characterEntity.getCharacterRol()));
		}

		return c;
	}

	@Override
	public List<RolDto> getRol(List<RolEntity> rolEntity) {

		List<RolDto> lista = new ArrayList<RolDto>();

		for (RolEntity r : rolEntity) {
			RolDto rolDto = new RolDto();
			rolDto.setName(Enums.rolUser.valueOf(r.getName()));
			lista.add(rolDto);
		}

		return lista;

	}

	@Override
	public CharacterRolDto getCharacterRol(CharacterRolEntity characterRolEntity) {
		CharacterRolDto chRolDto = new CharacterRolDto();

		chRolDto.setId(characterRolEntity.getRolId());
		chRolDto.setCharacterRolName(characterRolEntity.getCharacterRolName());
		chRolDto.setSkills(getSkillList(characterRolEntity.getSkills()));

		return chRolDto;
	}
	
	@Override
	public List<SkillDto> getSkillList(List<SkillEntity> entityList) {

		List<SkillDto> dtoList = new ArrayList<SkillDto>();
		
		for (SkillEntity s : entityList)
			dtoList.add(getSkill(s));

		return dtoList;

	}

	@Override
	public SkillDto getSkill(SkillEntity skill) {
		SkillDto skillDto = new SkillDto();
		
		List<CharacterRolDto> characterRolesDto = new ArrayList<CharacterRolDto>();

		if (skill.getIdSkill() != null) {
			skillDto.setIdSkill(skill.getIdSkill());
		}
		
		skillDto.setName(skill.getName());
		skillDto.setDescription(skill.getDescription());
		skillDto.setAction(skill.getAction());
		skillDto.setQuantity(skill.getQuantity());
		
		for (CharacterRolEntity rol : skill.getCharacterRoles()) {
			
			CharacterRolEntity cr = new CharacterRolEntity();
			cr.setRolId(rol.getRolId());
			cr.setCharacterRolName(rol.getCharacterRolName());
			
			characterRolesDto.add(getCharacterRol(cr));
			
		}
		
		skillDto.setCharacterRoles(characterRolesDto);
		return skillDto;
	}

}

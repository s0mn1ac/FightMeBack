package apps.somniac.fightme.converters.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import apps.somniac.fightme.converters.DtoToEntity;
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
import apps.somniac.fightme.repositories.CharacterRolRepository;

@Service
public class DtoToEntityImpl implements DtoToEntity {

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	CharacterRolRepository characterRolRepository;

	@Override
	public UserEntity getUser(UserDto userDto) {
		UserEntity userEntity = new UserEntity();
		if (userDto.getIdUser() != null) {
			userEntity.setIdUser(userDto.getIdUser());
		}
		userEntity.setUsername(userDto.getUserName());
		userEntity.setPassword(encoder.encode(userDto.getPassword()));
		userEntity.setMail(userDto.getMail());
		userEntity.setBirthdate(userDto.getBirthdate());
		userEntity.setCountry(userDto.getCountry());
		userEntity.setRoles(getRol(userDto.getRol()));
		userEntity.setFaceId(userDto.getFaceId());
		//userEntity.setImage(userDto.getImage());

		return userEntity;
	}

	@Override
	public CharacterEntity getCharacter(CharacterDto characterDto) {
		CharacterEntity character = new CharacterEntity();

		if (characterDto.getIdCharacter() != null) {
			character.setIdCharacter(characterDto.getIdCharacter());
		}
		character.setLvl(characterDto.getLvl());
		character.setExperience(characterDto.getExperience());
		character.setHp(characterDto.getHp());
		character.setIntelligence(characterDto.getIntelligence());
		character.setMagic(characterDto.getMagic());
		character.setName(characterDto.getName());
		character.setSpeed(characterDto.getSpeed());
		character.setStrength(characterDto.getStrength());
		//character.setImg(characterDto.getCharacterRol().getCharacterRolName().toLowerCase() + ".png");
		if (characterDto.getVictories() != null) {
			character.setVictories(characterDto.getVictories());
		}
		if (characterDto.getDefeats() != null) {
			character.setDefeats(characterDto.getDefeats());
		}
		if (characterDto.getUser() != null) {
			character.setUser(getUser(characterDto.getUser()));
		}
		if (characterDto.getCharacterRol() != null) {
			character.setCharacterRol(getCharacterRol(characterDto.getCharacterRol()));
		}

		return character;
	}

	@Override
	public List<RolEntity> getRol(RolDto rolDto) {

		List<RolEntity> lista = new ArrayList<RolEntity>();

		RolEntity rolEntity = new RolEntity();
		rolEntity.setName(rolDto.getName().toString());
		lista.add(rolEntity);

		return lista;

	}

	@Override
	public CharacterRolEntity getCharacterRol(CharacterRolDto characterRolDto) {

		CharacterRolEntity chRol = new CharacterRolEntity();

		if (characterRolDto.getId() != null)
			chRol.setRolId(characterRolDto.getId());

		if (characterRolDto.getSkills() != null)
			chRol.setSkills(getSkillList(characterRolDto.getSkills()));

		chRol.setCharacterRolName(characterRolDto.getCharacterRolName());

		return chRol;

	}

	@Override
	public List<SkillEntity> getSkillList(List<SkillDto> dtoList) {

		List<SkillEntity> entityList = new ArrayList<SkillEntity>();

		for (SkillDto s : dtoList)
			entityList.add(getSkill(s));

		return entityList;

	}

	@Override
	public SkillEntity getSkill(SkillDto skillDto) {
		SkillEntity skill = new SkillEntity();
		List<CharacterRolEntity> characterRoles = new ArrayList<CharacterRolEntity>();

		if (skillDto.getIdSkill() != null) {
			skill.setIdSkill(skillDto.getIdSkill());
		}
		skill.setName(skillDto.getName());
		skill.setDescription(skillDto.getDescription());
		skill.setAction(skillDto.getAction());
		skill.setQuantity(skillDto.getQuantity());
		for (CharacterRolDto rolDto : skillDto.getCharacterRoles()) {
			characterRoles.add(characterRolRepository.findByCharacterRolName(rolDto.getCharacterRolName()));
		}
		skill.setCharacterRoles(characterRoles);
		return skill;
	}
}

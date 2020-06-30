package apps.somniac.fightme.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import apps.somniac.fightme.converters.DtoToEntity;
import apps.somniac.fightme.converters.EntityToDto;
import apps.somniac.fightme.dtos.CharacterRolDto;
import apps.somniac.fightme.dtos.SkillDto;
import apps.somniac.fightme.entities.SkillEntity;
import apps.somniac.fightme.exceptions.SkillNoContentException;
import apps.somniac.fightme.exceptions.messages.DataErrorMessages;
import apps.somniac.fightme.repositories.CharacterRolRepository;
import apps.somniac.fightme.repositories.SkillRepository;
import apps.somniac.fightme.services.SkillService;

@Service
public class SkillServiceImpl implements SkillService {

	private Logger logger = LoggerFactory.getLogger(SkillServiceImpl.class);
	
	private List<SkillDto> skillsRol = new ArrayList<SkillDto>();

	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private CharacterRolRepository characterRolRepository;

	@Autowired
	EntityToDto etd;

	@Autowired
	DtoToEntity dte;
	
	@Override
	@Transactional
	public void loadSkills(List<SkillDto> skills) {
		for (SkillDto skillDto : skills) {
			skillsRol.clear();
			for (CharacterRolDto rolDto : skillDto.getCharacterRoles()) {
				if (characterRolRepository.findByCharacterRolName(rolDto.getCharacterRolName()) != null) {
					skillsRol.add(skillDto);
			}
			rolDto.setSkills(skillsRol);
		}
			skillRepository.save(dte.getSkill(skillDto));
		}
		
	}
	
	// addSkill: Añade una nueva skill a la BBDD
	@Override
	@Transactional
	public void addSkill(SkillDto skill) {
		skillRepository.save(dte.getSkill(skill));
		
	}

	// modifySkill: Modifica los datos de una skill, pasando su nombre y los nuevos datos por parámetro
	@Override
	@Transactional
	public SkillDto modifySkill(Long id, SkillDto skill) {

		SkillEntity s = skillRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SKILL_NO_CONTENT);
			throw new SkillNoContentException(DataErrorMessages.SKILL_NO_CONTENT);
		});

		s = dte.getSkill(skill);
		s.setIdSkill(id);
		skillRepository.save(s);
		
		return skill;

	}

	// deleteSkill: Elimina una skill de la BBDD
	@Override
	@Transactional
	public void deleteSkill(Long id) {

		SkillEntity s = skillRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SKILL_NO_CONTENT);
			throw new SkillNoContentException(DataErrorMessages.SKILL_NO_CONTENT);
		});

		// TODO Lógica de borrado

		skillRepository.delete(s);
		
	}

	// getSkills: Muestra todas las skills registradas en la BBDD
	@Override
	@Transactional(readOnly = true)
	public List<SkillDto> getSkills() {

		List<SkillEntity> skillEntities = skillRepository.findAll();
		List<SkillDto> skillDTOs = new ArrayList<>();

		for (SkillEntity s : skillEntities)
			skillDTOs.add(etd.getSkill(s));
		return skillDTOs;
		
	}

	// getSkill: Muestra los datos de una única skill
	@Override
	public SkillDto getSkill(Long id) {
		
		return etd.getSkill(skillRepository.findById(id).orElseThrow(() -> {
			logger.warn(DataErrorMessages.SKILL_NO_CONTENT);
			throw new SkillNoContentException(DataErrorMessages.SKILL_NO_CONTENT);
		}));
		
	}
	
	// getSkillsPerPage: Muestra todas las skills registradas en la BBDD de manera paginada
	@Override
	@Transactional(readOnly = true)
	public Page<SkillDto> getSkillsPerPage(Integer page) {
		
		Page<SkillEntity> paginator = skillRepository.findAll(PageRequest.of(page, 5));
		
		Page<SkillDto> paginatorDto = paginator.map(new Function<SkillEntity, SkillDto>() {
			@Override
			public SkillDto apply(SkillEntity d) {
				return etd.getSkill(d);
			}
		});

		return paginatorDto;
		
	}

	@Override
	public List<SkillDto> getSkillByName(String name) {
		System.out.println("Entra en la función: " + name);
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------");
		List<SkillEntity> listaCompleta = skillRepository.findAll();
		List<SkillDto> listaFinalDtos = new ArrayList<>();
		
		for (SkillEntity s : listaCompleta) {
			if (s.getName().toLowerCase().indexOf(name.toLowerCase()) != -1) {
				listaFinalDtos.add(etd.getSkill(s));
			}
		}
		
		System.out.println("Tamaño lista coincidencias: " + listaFinalDtos.size());
		return listaFinalDtos;
	}


}

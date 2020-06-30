package apps.somniac.fightme.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import apps.somniac.fightme.dtos.SkillDto;

@Service
public interface SkillService {
	public void loadSkills(List<SkillDto> skills);
	public void addSkill(SkillDto skill);
	public SkillDto modifySkill(Long id, SkillDto skill);
	public void deleteSkill(Long id);
	public List<SkillDto> getSkills();
	public SkillDto getSkill(Long id);
	public Page<SkillDto> getSkillsPerPage(Integer page);
	public List<SkillDto>getSkillByName(String name);
}

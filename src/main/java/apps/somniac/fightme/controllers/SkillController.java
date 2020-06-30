package apps.somniac.fightme.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apps.somniac.fightme.dtos.SkillDto;
import apps.somniac.fightme.services.impl.SkillServiceImpl;

@RestController
@RequestMapping("/skill")
public class SkillController {

	@Autowired
	private SkillServiceImpl skillService;

	@Secured("ROLE_ADMIN")
	// POST (CARGA INICIAL)
	@PostMapping("/loadall")
	public void loadSkills(@Validated @RequestBody List<SkillDto> skills) {
		skillService.loadSkills(skills);
	};

	@Secured("ROLE_ADMIN")
	// GET
	@GetMapping("/{id}")
	public SkillDto getSkill(@Validated @PathVariable Long id) {
		return skillService.getSkill(id);
		}


	@Secured("ROLE_ADMIN")
	// POST
	@PostMapping
	public void addSkill(@Validated @RequestBody SkillDto skill) {
		skillService.addSkill(skill);
	};

	@Secured("ROLE_ADMIN")
	// DELETE
	@DeleteMapping("/{id}")
	public void deleteSkill(@PathVariable Long id) {
		skillService.deleteSkill(id);
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	// GET
	@GetMapping
	public List<SkillDto> getSkills() {
		return skillService.getSkills();
	}

	@Secured("ROLE_ADMIN")
	// PUT
	@PutMapping("/{id}")
	public SkillDto modifySkill(@Validated @PathVariable Long id, @Validated @RequestBody SkillDto skill) {
		return skillService.modifySkill(id, skill);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/page/{page}")
	public Page<SkillDto> getSkillsPerPage(@PathVariable Integer page) {
		return skillService.getSkillsPerPage(page);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	// GET
	@GetMapping("/page/*/{name}")
	public List<SkillDto> getSkillbyName(@PathVariable String name) {
		return skillService.getSkillByName(name);
	}
	
}

package apps.somniac.fightme.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apps.somniac.fightme.dtos.CharacterDto;
import apps.somniac.fightme.services.CharacterService;

@RestController
@RequestMapping("/character")
public class CharacterController {

	@Autowired
	private CharacterService characterService;

	// GET
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/{id}")
	public CharacterDto getCharacter(@Validated @PathVariable Long id) {
		return characterService.getCharacter(id);
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping
	public List<CharacterDto> getCharacters() {
		return characterService.getCharacters();
	}
	
	@GetMapping("/uploads/img/{img:.+}")
	public ResponseEntity<Resource> imgViewCharacter(@PathVariable String img) {
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + img + "\"");
		return new ResponseEntity<Resource>(characterService.imgViewCharacter(img), header, HttpStatus.OK);
	}
	
	// POST
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public void addCharacter(@Validated @RequestBody CharacterDto character) {
		characterService.addCharacter(character);
	};

	// DELETE
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public void deleteCharacter(@PathVariable Long id) {
		characterService.deleteCharacter(id);
	}

	// PUT
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public CharacterDto modifyCharacter(@Validated @PathVariable Long id, @Validated @RequestBody CharacterDto character) {
		return characterService.modifyCharacter(id, character);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/page/{page}")
	public Page<CharacterDto> getCharactersPerPage(@PathVariable Integer page) {
		return characterService.getCharactersPerPage(page);
	}
	
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	// GET
	@GetMapping("/page/*/{name}")
	public List<CharacterDto> getSkillbyName(@PathVariable String name) {
		return characterService.getCharacterByName(name);
	}

}

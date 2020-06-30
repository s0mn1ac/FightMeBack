package apps.somniac.fightme.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import apps.somniac.fightme.dtos.CharacterRolDto;
import apps.somniac.fightme.services.CharacterRolService;

@RestController
@RequestMapping("/characterRol")
public class CharacterRolController {

	@Autowired
	private CharacterRolService chRepo;

	@Secured({"ROLE_ADMIN"})
	// GET
	@GetMapping("/{id}")
	public CharacterRolDto getCharacter(@Validated @PathVariable Long id) {
		return chRepo.getCharacterRol(id);
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping
	public List<CharacterRolDto> getCharacters() {
		return chRepo.getCharactersRol();
	}

	// POST
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public void addCharacter(@Validated @RequestBody CharacterRolDto characterRol) {
		chRepo.addCharacterRol(characterRol);
	};

	// DELETE
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public void deleteCharacter(@PathVariable Long id) {
		chRepo.deleteCharacterRol(id);
	}

	// PUT
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public CharacterRolDto modifyCharacter(@Validated @PathVariable Long id,
			@Validated @RequestBody CharacterRolDto characterRol) {
		return chRepo.modifyCharacterRol(characterRol, id);
	}

}

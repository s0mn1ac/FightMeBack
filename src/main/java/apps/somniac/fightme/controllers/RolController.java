package apps.somniac.fightme.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apps.somniac.fightme.dtos.RolDto;
import apps.somniac.fightme.services.RolService;

@Secured({"ROLE_ADMIN"})
@RestController
@RequestMapping("/roles")
public class RolController {
	
	@Autowired
	private RolService rolService;
	
	@GetMapping
	public List<RolDto> getRoles() {
		return rolService.getRoles();
	}
	
	@GetMapping("/{id}")
	public RolDto getRol(@Validated @PathVariable Long id) {
		return rolService.getRol(id);
	}

}

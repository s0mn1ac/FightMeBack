package apps.somniac.fightme.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import apps.somniac.fightme.dtos.FaceIdDto;
import apps.somniac.fightme.dtos.UserDto;
import apps.somniac.fightme.services.FaceIdService;

@RestController
@RequestMapping("/faceid")
public class FaceIdController {

	@Autowired
	private FaceIdService faceIdService;
	
	// facialRecognition: Comprueba si una foto pasada por parámetro pertenece a un personId específico
	@PostMapping("/{username}")
	public FaceIdDto facialRecognition(@PathVariable String username, @RequestBody String image) {
		return faceIdService.facialRecognition(username, image);
	}
	
	// createPersonId: Registra un personId con el nombre de usuario correspondiente al pasado por parámetro
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/enable/{id}")
	public UserDto createPersonId(@PathVariable Long id) {
		return faceIdService.createPersonId(id);
	}
	
	// deletePersonId: Borra el personId asociado a un usuario en concreto
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public UserDto deletePersonId(@PathVariable Long id) {
		return faceIdService.deletePersonId(id);
	}
	
	// fillPersonId: Añade información obtenida de diferentes fotografías a un personId específico
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/fill/{id}")
	public void fillPersonId(@RequestParam MultipartFile file, @PathVariable Long id) {
		faceIdService.fillPersonId(file, id);
	}
	
	// createPersonId: Registra un personId con el nombre de usuario correspondiente al pasado por parámetro
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/list")
	public FaceIdDto[] listPersonId() {
		return faceIdService.listPersonId();
	}

}

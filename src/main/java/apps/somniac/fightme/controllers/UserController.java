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

import apps.somniac.fightme.dtos.UserDto;
import apps.somniac.fightme.services.UserService;

//@Secured({"ROLE_ADMIN"})
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	// getUser: Muestra los datos de un único usuario (buscando por id)
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/{id}")
	public UserDto getUser(@Validated @PathVariable Long id) {
		return userService.getUser(id);
	}
	
	// getUserByUsername: Muestra los datos de un único usuario (buscando por username)
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/checkUser/{userName}")
	public UserDto getUserByUsername(@Validated @PathVariable String userName) {
		return userService.getUserByUserName(userName);
	}

	// addUser: Añade un nuevo usuario a la BBDD
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public void addUser(@Validated @RequestBody UserDto user) {
		userService.addUser(user);
	};

	// deleteUser: Elimina un usuario de la BBDD
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}

	// getUsers: Muestra los datos de todos los usuarios registrados en la BBDD
	@Secured({"ROLE_ADMIN"})
	@GetMapping
	public List<UserDto> getUsers() {
		return userService.getUsers();
	}

	// modifyUser: Modifica los datos de un usuario, pasando su id y los nuevos datos por parámetro
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public UserDto modifyUser(@Validated @PathVariable Long id, @Validated @RequestBody UserDto user) {
		return userService.modifyUser(id, user);
	}
	
	// modifyPassword: Actualiza la contraseña de un usuario
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/password/{id}")
	public UserDto modifyPassword(@Validated @PathVariable Long id, @Validated @RequestBody UserDto user) {
		return userService.modifyPassword(id, user);
	}
	
	// getUsersPerPage: Muestra todos los usuarios registrados en la BBDD de manera paginada
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/page/{page}")
	public Page<UserDto> getUsersPerPage(@PathVariable Integer page) {
		return userService.getUsersPerPage(page);
	}

	// getUserbyName: Devuelve de manera paginada los usuarios que coincidad con el nombre pasado por parámetro
	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/page/*/{userName}")
	public List<UserDto> getUserbyName(@PathVariable String userName) {
		return userService.getUserByName(userName);
	}
	
}

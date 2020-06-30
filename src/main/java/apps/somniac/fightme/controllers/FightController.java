package apps.somniac.fightme.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import apps.somniac.fightme.dtos.CharacterDto;
import apps.somniac.fightme.dtos.FightDto;
import apps.somniac.fightme.dtos.ManualFightDto;
import apps.somniac.fightme.services.FightService;

@Secured({ "ROLE_ADMIN", "ROLE_USER" })
@RestController
@RequestMapping("/fight")
public class FightController {

	@Autowired
	private FightService fightService;

	@PutMapping
	public FightDto randomFight(@Validated @RequestBody List<CharacterDto> players) {
		return fightService.randomFight(players);
	}
	
	@PutMapping("/control") //TODO: Revisar como llegan los parametros
	public FightDto controlFight(@RequestBody ManualFightDto mFight) {
		return fightService.controlFight(mFight);
	}

}

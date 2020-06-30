package apps.somniac.fightme.services;

import java.util.List;

import org.springframework.stereotype.Service;

import apps.somniac.fightme.dtos.CharacterDto;
import apps.somniac.fightme.dtos.FightDto;
import apps.somniac.fightme.dtos.ManualFightDto;

@Service
public interface FightService {
	
	public FightDto randomFight(List<CharacterDto> players);
	public FightDto controlFight(ManualFightDto mFight);

}

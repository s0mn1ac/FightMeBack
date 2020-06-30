package apps.somniac.fightme.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class FightDto {
	
	private List<SkillDto> movesPlayer1 = new ArrayList<SkillDto>();
	private List<SkillDto> movesPlayer2 = new ArrayList<SkillDto>();
	private CharacterDto winner;
	private CharacterDto faster;
	private List<SkillDto> allMoves;
	private SkillDto moveOpponent;
	private Long hp1;
	private Long hp2;

}

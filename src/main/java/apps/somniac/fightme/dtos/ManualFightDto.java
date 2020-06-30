package apps.somniac.fightme.dtos;

import lombok.Data;

@Data
public class ManualFightDto {
	CharacterDto user;
	CharacterDto opponent;
	SkillDto moveUser;
	Long newHP1;
	Long newHP2;
	//CharacterDto winner;
	

}

package apps.somniac.fightme.dtos;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;

@Data
public class CharacterRolDto {
	
	private Long id;
	
	private String characterRolName;
	
	private List<CharacterDto> characters = new ArrayList<CharacterDto>();
	
	private List<SkillDto> skills = new ArrayList<SkillDto>();

}

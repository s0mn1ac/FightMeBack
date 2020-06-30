package apps.somniac.fightme.dtos;

import java.util.ArrayList;
import java.util.List;

import apps.somniac.fightme.enums.Enums;
import lombok.Data;


@Data
public class SkillDto {
	
	private Long idSkill;
	private String name;
	private String description;
	private Enums.skillAction action;
	private Integer quantity;
	private List<CharacterRolDto> characterRoles = new ArrayList<CharacterRolDto>();
	
	

}

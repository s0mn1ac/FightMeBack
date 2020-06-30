package apps.somniac.fightme.dtos;

import lombok.Data;

@Data
public class CharacterDto {

	private Long idCharacter;
	private String name;
	private Integer lvl;
	private Long experience;
	private Long strength;
	private Long magic;
	private Long hp;
	private Long speed;
	private Long intelligence;
	private UserDto user;
	private String img;
	private Integer victories;
	private Integer defeats;
	private CharacterRolDto characterRol;

}

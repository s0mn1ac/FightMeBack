package apps.somniac.fightme.dtos;

import apps.somniac.fightme.enums.Enums;
import lombok.Data;

@Data
public class RolDto {
	
	private long id;
	
	private Enums.rolUser name;

}

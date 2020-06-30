package apps.somniac.fightme.dtos;

import lombok.Data;

@Data
public class FaceIdDto {
	
	private String personId;
	private String persistedFaceIds[];
	private String name;
	
}

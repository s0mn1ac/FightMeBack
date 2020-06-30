package apps.somniac.fightme.dtos;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserDto {

	private Long idUser;

	private String userName;

	private String password;

	private String mail;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private String birthdate;

	private String country;
	
	private RolDto rol;
	
	private String faceId;

	//private List<RolDto> roles = new ArrayList<RolDto>();
	
	//private List<String> images = new ArrayList<String>();
	
	//private String image;
	
	private List<CharacterDto> characters = new ArrayList<CharacterDto>();
	
	

}

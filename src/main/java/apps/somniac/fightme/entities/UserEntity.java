package apps.somniac.fightme.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Entity
@Table(name = "GAME_USER", uniqueConstraints = {@UniqueConstraint(columnNames = { "USER_NAME", "MAIL" })})
@Data
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ID_GAME_USER")
	@Column(name = "ID")
	private Long idUser;
	
	@Column(name = "USER_NAME")
	private String username;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "MAIL")
	private String mail;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "BIRTH_DATE")
	private String birthdate;
	
	@Column(name = "COUNTRY")
	private String country;
	
	@Column(name = "FACE_ID")
	private String faceId;

	//@Column(name = "IMAGE")
	//private String image;
	
	@ManyToMany(mappedBy = "users")
	private List<RolEntity> roles = new ArrayList<RolEntity>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<CharacterEntity> characters = new ArrayList<CharacterEntity>();

}

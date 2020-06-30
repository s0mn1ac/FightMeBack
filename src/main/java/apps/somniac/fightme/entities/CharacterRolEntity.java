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

import lombok.Data;

@Data
@Table(name = "GAME_CHARACTER_ROL")
@Entity
public class CharacterRolEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ID_GAME_CHARACTER_ROL")
	@Column(name = "ID")
	private Long rolId;

	@Column(name = "CHARACTER_ROL_NAME")
	private String characterRolName;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "characterRol")
	private List<CharacterEntity> characters = new ArrayList<CharacterEntity>();

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "characterRoles")
	private List<SkillEntity> skills = new ArrayList<SkillEntity>();
}

package apps.somniac.fightme.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import apps.somniac.fightme.enums.Enums;
import lombok.Data;

@Data
@Table(name = "SKILL")
@Entity

public class SkillEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ID_SKILL")
	@Column(name = "ID")
	private Long idSkill;
	
	@Column(name = "NOMBRE")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "ACTION")
	private Enums.skillAction action;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@ManyToMany
	private List<CharacterRolEntity> characterRoles = new ArrayList<CharacterRolEntity>();

}

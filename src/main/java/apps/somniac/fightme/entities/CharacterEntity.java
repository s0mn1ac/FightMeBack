package apps.somniac.fightme.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Entity
@Table(name = "GAME_CHARACTER", uniqueConstraints = {@UniqueConstraint(columnNames = { "NAME" })})
@Data
public class CharacterEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ID_GAME_CHARACTER")
	private Long idCharacter;

	@Column(name = "NAME")
	private String name;

	@Column(name = "LVL")
	private Integer lvl;

	@Column(name = "EXPERIENCE")
	private Long experience;

	@Column(name = "STRENGTH")
	private Long strength;

	@Column(name = "MAGIC")
	private Long magic;

	@Column(name = "HEALTHPOINTS")
	private Long hp;

	@Column(name = "SPEED")
	private Long speed;

	@Column(name = "INTELLIGENCE")
	private Long intelligence;
	
	@Column(name = "IMG")
	private String img;
	
	@Column(name = "VICTORIES")
	private Integer victories;
	
	@Column(name = "DEFEATS")
	private Integer defeats;

	@ManyToOne
	private UserEntity user;
	
	@ManyToOne
	private CharacterRolEntity characterRol;

}

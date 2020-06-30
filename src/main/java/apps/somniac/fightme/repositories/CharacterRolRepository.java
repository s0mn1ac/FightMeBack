package apps.somniac.fightme.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import apps.somniac.fightme.entities.CharacterRolEntity;

public interface CharacterRolRepository extends JpaRepository<CharacterRolEntity, Long>{
	public CharacterRolEntity findByCharacterRolName(String characterRolName);
}

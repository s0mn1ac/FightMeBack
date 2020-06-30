package apps.somniac.fightme.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import apps.somniac.fightme.entities.CharacterEntity;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long>{
	public Optional<CharacterEntity> findByName(String name);
	public Page<CharacterEntity> findAll(Pageable pageable);
}

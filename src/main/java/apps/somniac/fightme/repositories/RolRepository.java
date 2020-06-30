package apps.somniac.fightme.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import apps.somniac.fightme.entities.RolEntity;

public interface RolRepository extends JpaRepository<RolEntity, Long> {
	
	public Optional<RolEntity> findByName(String name);
	
}

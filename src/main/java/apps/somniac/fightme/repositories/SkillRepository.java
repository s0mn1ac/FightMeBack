package apps.somniac.fightme.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import apps.somniac.fightme.entities.SkillEntity;

public interface SkillRepository extends JpaRepository<SkillEntity, Long> {
	public List<SkillEntity> findByName(String name);
	public Optional<SkillEntity> findById(Long id);
	public Page<SkillEntity> findAll(Pageable pageable);
}

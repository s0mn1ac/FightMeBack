package apps.somniac.fightme.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import apps.somniac.fightme.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
	public Optional<UserEntity> findByUsername(String username);
	public Optional<UserEntity> findByMail(String email);
	public Page<UserEntity> findAll(Pageable pageable);
}
package apps.somniac.fightme.services;

import java.util.Optional;

import apps.somniac.fightme.entities.UserEntity;

public interface LoginService {
	
	public Optional<UserEntity> findByUsername(String username);

}

package apps.somniac.fightme.services;

import java.util.List;

import org.springframework.stereotype.Service;

import apps.somniac.fightme.dtos.RolDto;

@Service
public interface RolService {
	
	public RolDto getRol(Long id);
	public List<RolDto> getRoles();

}

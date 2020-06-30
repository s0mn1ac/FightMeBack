package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.BadRequestException;

public class SkillAlreadyExistsException extends BadRequestException {
	
	private static final long serialVersionUID = 1L;
	
	public SkillAlreadyExistsException(String details) { 
		super(details);
	}

}

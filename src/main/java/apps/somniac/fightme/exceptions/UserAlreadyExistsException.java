package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.BadRequestException;

public class UserAlreadyExistsException extends BadRequestException {
	
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyExistsException(String details) {
		super(details);
	}

}

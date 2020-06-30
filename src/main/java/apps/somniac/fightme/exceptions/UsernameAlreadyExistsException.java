package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.ConstraintViolationException;

public class UsernameAlreadyExistsException extends ConstraintViolationException {
	
	private static final long serialVersionUID = 1L;
	
	public UsernameAlreadyExistsException(String details) {
		super(details);
	}

}

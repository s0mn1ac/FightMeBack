package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.ConstraintViolationException;

public class EmailAlreadyExistsException extends ConstraintViolationException {
	
	private static final long serialVersionUID = 1L;
	
	public EmailAlreadyExistsException(String details) {
		super(details);
	}

}

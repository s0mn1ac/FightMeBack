package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.ConstraintViolationException;

public class CharacterNameAlreadyExistsException extends ConstraintViolationException {
	
	private static final long serialVersionUID = 1L;
	
	public CharacterNameAlreadyExistsException(String details) {
		super(details);
	}

}

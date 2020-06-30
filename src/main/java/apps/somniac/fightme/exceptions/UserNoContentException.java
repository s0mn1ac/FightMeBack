package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.NoContentException;

public class UserNoContentException extends NoContentException {
	
	private static final long serialVersionUID = 1L;
	
	public UserNoContentException(String details) {
		super(details);
	}

}

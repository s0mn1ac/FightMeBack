package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.NoContentException;

public class RoleNoContentException extends NoContentException {
	
	private static final long serialVersionUID = 1L;
	
	public RoleNoContentException(String details) {
		super(details);
	}

}

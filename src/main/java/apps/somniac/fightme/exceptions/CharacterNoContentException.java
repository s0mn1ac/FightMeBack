package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.NoContentException;

public class CharacterNoContentException extends NoContentException {
	
	private static final long serialVersionUID = 1L;
	
	public CharacterNoContentException(String details) {
		super(details);
	}

}

package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.NoContentException;

public class PersonIdNoContentException extends NoContentException{

	private static final long serialVersionUID = 1L;

	public PersonIdNoContentException(String details) {
		super(details);
	}

}

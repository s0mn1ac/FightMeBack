package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.NoContentException;

public class CharacterRolNoContentException extends NoContentException{

	private static final long serialVersionUID = 2033671670143648789L;

	public CharacterRolNoContentException(String details) {
		super(details);
	}

}

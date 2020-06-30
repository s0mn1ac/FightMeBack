package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.NoContentException;

public class GameOverException extends NoContentException {
	
	private static final long serialVersionUID = 1L;
	
	public GameOverException(String details) {
		super(details);
	}

}

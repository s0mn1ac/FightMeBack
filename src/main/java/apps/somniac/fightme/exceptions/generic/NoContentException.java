package apps.somniac.fightme.exceptions.generic;

public class NoContentException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public NoContentException(String details) {
		super(details);
	}

}

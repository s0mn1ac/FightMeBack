package apps.somniac.fightme.exceptions.generic;

public class ConstraintViolationException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public ConstraintViolationException(String details) {
		super(details);
	}

}

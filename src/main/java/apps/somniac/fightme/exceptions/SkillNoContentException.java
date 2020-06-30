package apps.somniac.fightme.exceptions;

import apps.somniac.fightme.exceptions.generic.NoContentException;

public class SkillNoContentException extends NoContentException {

	private static final long serialVersionUID = 2033671670143648789L;

	public SkillNoContentException(String details) {
		super(details);
	} 

}

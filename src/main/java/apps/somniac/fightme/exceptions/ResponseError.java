package apps.somniac.fightme.exceptions;

import lombok.Data;

@Data
public class ResponseError {
	private String exception;
	private String errorMessage;
	private String path;

	public ResponseError(Exception exception, String errorMessage, String path) {
		super();
		this.exception = exception.getClass().getSimpleName();
		this.errorMessage = errorMessage;
		this.path = path;
	}

	public ResponseError(Exception exception, String errorMessage) {
		super();
		this.exception = exception.getClass().getSimpleName();
		this.errorMessage = errorMessage;
	}

}

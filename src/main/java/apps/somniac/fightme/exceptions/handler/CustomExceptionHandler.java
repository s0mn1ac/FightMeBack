package apps.somniac.fightme.exceptions.handler;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import apps.somniac.fightme.exceptions.ResponseError;
import apps.somniac.fightme.exceptions.generic.BadRequestException;
import apps.somniac.fightme.exceptions.generic.NoContentException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler({ NoContentException.class })
	@ResponseBody
	public ResponseError noContentException(HttpServletRequest request, Exception exception) {
		return new ResponseError(exception, request.getRequestURI());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ BadRequestException.class })
	@ResponseBody
	public ResponseError badRequestException(HttpServletRequest request, Exception exception) {
		return new ResponseError(exception, request.getRequestURI());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getDefaultMessage())
				.collect(Collectors.toList());
		return new ResponseEntity<Object>(new ResponseError(ex, errorMessages.toString(), request.getContextPath()),
				HttpStatus.BAD_REQUEST);
	}

}

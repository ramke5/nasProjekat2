package ba.nalaz.web.helper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import ba.nalaz.model.core.ProductConstants;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ResourceForbiddenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ResourceForbiddenException() {
        super(ProductConstants.ACCESS_DENIED);
    }

    public ResourceForbiddenException(String message) {
        super(message);
    }
}
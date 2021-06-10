package xws.auth.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import xws.auth.exception.UsernameNotUniqueException;

public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = UsernameNotUniqueException.class)
    public ResponseEntity<Object> handleUsernameNotUniqueException(UsernameNotUniqueException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

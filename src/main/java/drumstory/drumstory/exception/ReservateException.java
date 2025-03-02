package drumstory.drumstory.exception;

import org.springframework.http.HttpStatus;

public class ReservateException extends MessageException {
    public ReservateException(String message, HttpStatus status) {
        super(message, status);
    }
}
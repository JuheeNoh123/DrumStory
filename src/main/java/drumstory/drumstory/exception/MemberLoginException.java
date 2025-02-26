package drumstory.drumstory.exception;

import org.springframework.http.HttpStatus;


public class MemberLoginException extends MessageException {

    public MemberLoginException(String message, HttpStatus status) {
        super(message, status);
    }
}
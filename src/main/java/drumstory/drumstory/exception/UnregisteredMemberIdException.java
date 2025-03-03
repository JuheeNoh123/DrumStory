package drumstory.drumstory.exception;

import org.springframework.http.HttpStatus;



public class UnregisteredMemberIdException extends MessageException {

    public UnregisteredMemberIdException(String message, HttpStatus status) {
        super(message, status);
    }

}
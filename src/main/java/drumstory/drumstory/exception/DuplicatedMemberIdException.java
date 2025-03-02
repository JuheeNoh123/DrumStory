package drumstory.drumstory.exception;


import org.springframework.http.HttpStatus;

public class DuplicatedMemberIdException extends MessageException {
    public DuplicatedMemberIdException(String message, HttpStatus status) {
        super(message, status);
    }
}


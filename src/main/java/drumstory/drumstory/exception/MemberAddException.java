package drumstory.drumstory.exception;


import org.springframework.http.HttpStatus;

public class MemberAddException extends MessageException {

    public MemberAddException(String message, HttpStatus status) {
        super(message, status);
    }

}

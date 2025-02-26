package drumstory.drumstory.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberLoginException.class)
    public ResponseEntity<String> handleMemberLoginException(MemberLoginException ex) {
        // 예외가 발생하면 400 상태 코드와 메시지를 반환
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
}
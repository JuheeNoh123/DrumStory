package drumstory.drumstory.controller;
import drumstory.drumstory.exception.DuplicatedMemberIdException;
import drumstory.drumstory.exception.MemberLoginException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
    //중복된 ID가 있으면 "이미 등록된 회원 ID 입니다." 메시지와 함께 409 CONFLICT 상태 코드가 반환
    @ExceptionHandler(DuplicatedMemberIdException.class)
    public ResponseEntity<String> DuplicateMemberId(DuplicatedMemberIdException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

    //로그인 실패 시 "등록되지 않은 ID 입니다." , 400 에러 반환
    @ExceptionHandler(MemberLoginException.class)
    public ResponseEntity<String> handleMemberLoginException(MemberLoginException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }

}

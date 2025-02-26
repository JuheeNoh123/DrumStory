package drumstory.drumstory.controller;

import drumstory.drumstory.DTO.MemberDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<MemberDTO.ResponseLogin> login(@RequestBody MemberDTO.MemberInfo request) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.login(request));
    }

}

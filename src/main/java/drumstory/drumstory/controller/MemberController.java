package drumstory.drumstory.controller;

import drumstory.drumstory.DTO.MemberDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "회원 페이지")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "로그인 기능 (주희)", description = "관리자는 토큰 필요",
            responses = {@ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 ID")})
    @PostMapping("/login")
    public ResponseEntity<MemberDTO.ResponseLogin> login(@RequestBody MemberDTO.MemberInfo request) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.login(request));
    }


}

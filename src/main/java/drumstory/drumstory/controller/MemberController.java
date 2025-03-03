package drumstory.drumstory.controller;

import drumstory.drumstory.DTO.MemberDTO;
import drumstory.drumstory.DTO.ReservationDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.service.TimeTable;
import drumstory.drumstory.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "회원 페이지")
public class MemberController {
    private final MemberService memberService;
    private final TimeTable availableTime;

    @Operation(summary = "로그인 기능 (주희)", description = "관리자는 토큰 필요",
            responses = {@ApiResponse(responseCode = "200", description = "로그인 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 ID")})
    @PostMapping("/login")
    public ResponseEntity<MemberDTO.ResponseLogin> login(@RequestBody MemberDTO.MemberInfo request) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.login(request));
    }


}

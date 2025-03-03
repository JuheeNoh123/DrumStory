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

    @GetMapping("/schedule")
    public List<ReservationDTO.ScheduleRes> getSchedule(HttpServletRequest header) {

        availableTime.getAvailableTimes();
        return null;
    }

    @Operation(summary = "시간 예약 (주희)", description = "해더에 토큰 필요",
            responses = {@ApiResponse(responseCode = "200", description = "시간 선택 완료"),
                    @ApiResponse(responseCode = "400", description = "최대 두 개의 시간만 선택 가능"),
                    @ApiResponse(responseCode = "400", description = "예약 시간을 선택해주세요(빈 리스트 일 경우)"),
                    @ApiResponse(responseCode = "400", description = "연속된 시간으로만 선택 가능합니다")
            })
    @PostMapping("/reservation/save")
    public ResponseEntity<ReservationDTO.ReservateTimeRes> saveReservation(HttpServletRequest header, @RequestBody ReservationDTO.ReservateTimeReq request ) {
        Member member = memberService.tokenToMember(header);
        ReservationDTO.ReservateTimeRes reservateTimeres = memberService.reservateTime(member, request.getTimes(), request.getResDate());
        return ResponseEntity.status(HttpStatus.OK).body(reservateTimeres);
    }



}

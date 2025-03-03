package drumstory.drumstory.controller;

import drumstory.drumstory.DTO.ReservationDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.service.MemberService;
import drumstory.drumstory.service.ReservationService;
import drumstory.drumstory.service.TimeTable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "회원 페이지-예약 관련(조회,예약,취소)")
public class MemberReservationController {
    private final MemberService memberService;
    private final ReservationService reservationService;
    private final TimeTable timeTable;

    @GetMapping("/schedule")
    public List<ReservationDTO.ScheduleRes> getSchedule(HttpServletRequest header) {

        reservationService.getAvailableTimes();
        return null;
    }

    @Operation(summary = "시간 예약 (주희)", description = "해더에 토큰 필요",
            responses = {@ApiResponse(responseCode = "200", description = "시간 선택 완료"),
                    @ApiResponse(responseCode = "400", description = "1. 최대 두 개의 시간만 선택 가능 <br>2. 예약 시간을 선택해주세요(빈 리스트 일 경우)<br>3. 연속된 시간으로만 선택 가능합니다"),
            })
    @PostMapping("/reservation/time")
    public ResponseEntity<ReservationDTO.ReservateTimeRes> ReservationTime(HttpServletRequest header, @RequestBody ReservationDTO.ReservateTimeReq request ) {
        Member member = memberService.tokenToMember(header);
        ReservationDTO.ReservateTimeRes reservateTimeres = reservationService.reservationTime(member, request.getTimes(), request.getResDate());
        return ResponseEntity.status(HttpStatus.OK).body(reservateTimeres);
    }

    @PostMapping("/reservation/room")
    public  ResponseEntity<ReservationDTO.ReservateTimeRoomRes> saveReservation(HttpServletRequest header, @RequestBody ReservationDTO.ReservateTimeRoomReq request) {
        Member member = memberService.tokenToMember(header);
        return null;

    }
}

package drumstory.drumstory.controller;

import drumstory.drumstory.DTO.ReservationDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.service.MemberService;
import drumstory.drumstory.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "회원 페이지-예약 관련(조회,예약,취소)")
public class MemberReservationController {
    private final MemberService memberService;
    private final ReservationService reservationService;


    @Operation(summary = "시간 선택 (주희)", description = "해더에 토큰 필요",
            responses = {@ApiResponse(responseCode = "200", description = "시간 선택 완료"),
                    @ApiResponse(responseCode = "400", description = "1. 최대 두 개의 시간만 선택 가능 <br>2. 예약 시간을 선택해주세요(빈 리스트 일 경우)<br>3. 연속된 시간으로만 선택 가능합니다"),
            })
    @PostMapping("/reservation/time")
    public ResponseEntity<ReservationDTO.ReservationTimeRes> ReservationTime(HttpServletRequest header, @RequestBody ReservationDTO.ReservationTimeReq request ) {
        Member member = memberService.tokenToMember(header);
        ReservationDTO.ReservationTimeRes reservationTimeRes = reservationService.selectTime(member, request.getTimes(), request.getResDate());
        return ResponseEntity.status(HttpStatus.OK).body(reservationTimeRes);
    }

    @Operation(summary = "방 선택 후 예약 처리(주희)", description = "해더에 토큰 필요",
            responses = {@ApiResponse(responseCode = "201", description = "예약 완료")})
    @PostMapping("/reservation/room")
    public  ResponseEntity<ReservationDTO.ReservationTimeRoomRes> saveReservation(HttpServletRequest header, @RequestBody ReservationDTO.ReservationTimeRoomReq request) {
        Member member = memberService.tokenToMember(header);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.saveReservationTimeRoom(member,request.getTimes(),request.getResDate(),request.getRoomNum()));

    }

    @Operation(summary = "해당 날짜에 예약 가능한 시간 조회(주희)", description = "해더에 토큰 필요",
            responses = {@ApiResponse(responseCode = "200", description = "이미 예약 된 시간 제외하고 현재 16:10분이면 16:00부터 조회")})
    @PostMapping("/reservation")
    public ResponseEntity<ReservationDTO.AvailableTimesAndMember> AvailableTimes(HttpServletRequest header, @RequestBody ReservationDTO.DateReq req) {
        Member member = memberService.tokenToMember(header);
        return ResponseEntity.status(HttpStatus.OK).body(new ReservationDTO.AvailableTimesAndMember(member.getName(), reservationService.findAvailableTimes(req.getDate())));
    }
}

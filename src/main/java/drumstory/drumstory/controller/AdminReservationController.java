package drumstory.drumstory.controller;


import drumstory.drumstory.DTO.ReservationDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.service.AdminMemberService;
import drumstory.drumstory.service.MemberService;
import drumstory.drumstory.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "관리자 예약 관리 페이지")
public class AdminReservationController {

    private final MemberService memberService;
    private final ReservationService reservationService;

    @Operation(summary = "예약 조회(선우)", description = "토큰 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회")})
    @PostMapping("/admin/reservation")
    public List<ReservationDTO.ReservationListRes> getAllReservations(@RequestBody ReservationDTO.ReservationListReq req){
        return reservationService.findAll(req.getResDate());
    }

    @Operation(summary = "관리자 예약 취소(선우)", description = "헤더에 토큰 필요",
            responses = {@ApiResponse(responseCode = "204", description = "취소 성공")})
    @DeleteMapping("/admin/reservation/delete")
    public ResponseEntity<Void> adminDeleteReservation(@RequestBody ReservationDTO.DeleteReservationReq req) throws Exception {
        Member member = memberService.findById(req.getId());
        reservationService.deleteReservation(member);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

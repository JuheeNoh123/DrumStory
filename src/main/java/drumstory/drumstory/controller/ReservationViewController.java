package drumstory.drumstory.controller;

import drumstory.drumstory.DTO.ReservationDTO;
import drumstory.drumstory.service.AdminMemberService;
import drumstory.drumstory.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "당일 예약보기")
public class ReservationViewController {

    private final AdminMemberService adminMemberService;
    private final ReservationService reservationService;

    @Operation(summary = "당일예약 보기(선우)", description = "토큰 X",
            responses = {@ApiResponse(responseCode = "200", description = "조회")})
    @GetMapping("/todayReservation/{date}")
    public List<ReservationDTO.ReservationListRes> getTodayReservations(@PathVariable String date){
        return reservationService.findAll(LocalDate.parse(date));
    }

}

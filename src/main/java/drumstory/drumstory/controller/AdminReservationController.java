package drumstory.drumstory.controller;


import drumstory.drumstory.domain.Member;
import drumstory.drumstory.service.AdminMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "관리자 예약 관리 페이지")
public class AdminReservationController {

    private final AdminMemberService adminMemberService;

    @Operation(summary = "예약 조회(선우)", description = "토큰 필요",
            responses = {@ApiResponse(responseCode = "200", description = "조회")})
    @GetMapping("/admin/reservation")
    public List<Member> getAllReservations(){
        return adminMemberService.findAll();
    }
}

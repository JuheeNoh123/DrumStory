package drumstory.drumstory.DTO;

import drumstory.drumstory.domain.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class MemberDTO {
    @Data
    public static class ResponseLogin {
        @Schema(description = "JWT 토큰", example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMDAwMDAwMCIsImlhdCI6MTcxMTgxMDc2NSwiZXhwIjoyMDcxODEwNzY1fQ.2gbH5s0ODmTE59NRrFi9Fd8kqahHsfQqgHu6NQjjte1_4abMHmI6VfSKVI46SjftueKXSDFVr8WATiuf1ZMNzg")
        private String accessToken;
        @Schema(description = "권한", example = "ROLE_ADMIN")
        private RoleType role;

        @Schema(description = "예약정보가 없으면 null, 있으면 예약정보 리스트 반환", example = "null")
        private ReservationDTO.ReservationTimeRes reservationInfo;

        public ResponseLogin(String accessToken, RoleType role, ReservationDTO.ReservationTimeRes reservationInfo) {
            this.accessToken = accessToken;
            this.role = role;
            this.reservationInfo = reservationInfo;
        }
    }

    @Data
    public static class MemberInfo {
        @Schema(description = "회원ID", example = "4534")
        private String memberNum;

        public MemberInfo(String memberNum) {
            this.memberNum = memberNum;

        }
    }
}

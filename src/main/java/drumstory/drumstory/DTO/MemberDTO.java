package drumstory.drumstory.DTO;

import drumstory.drumstory.domain.RoleType;
import lombok.Data;

public class MemberDTO {
    @Data
    public static class ResponseLogin {
        //@Schema(description = "JWT 토큰", example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMDAwMDAwMCIsImlhdCI6MTcxMTgxMDc2NSwiZXhwIjoyMDcxODEwNzY1fQ.2gbH5s0ODmTE59NRrFi9Fd8kqahHsfQqgHu6NQjjte1_4abMHmI6VfSKVI46SjftueKXSDFVr8WATiuf1ZMNzg")
        private String accessToken;
        private RoleType role;

        public ResponseLogin(String accessToken, RoleType role) {
            this.accessToken = accessToken;
            this.role = role;
        }
    }

    @Data
    public static class MemberInfo {
        //@Schema(description = "회원ID", example = "4534")
        private String memberNum;

        public MemberInfo(String memberNum) {
            this.memberNum = memberNum;

        }
    }
}

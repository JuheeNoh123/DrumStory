package drumstory.drumstory.DTO;

import drumstory.drumstory.domain.RoleType;
import lombok.Data;

public class AdminMemberDTO {
    @Data
    public static class AdminCreateMemberDTO {
        private String name;
        private String phoneNumber;
        private String memberNum;
    }

    @Data
    public static class AddMemberResponse {
        //@Schema(description = "회원ID", example = "0000")
        private String memberNum;
        //@Schema(description = "이름", example = "홍길동")
        private String name;
        //@Schema(description = "권한", example = "ROLE_ADMIN")
        private RoleType role;

        public AddMemberResponse(String memberNum, String name, RoleType role) {
            this.memberNum = memberNum;
            this.name = name;
            this.role = role;
        }
    }


}

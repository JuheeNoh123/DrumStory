package drumstory.drumstory.DTO;

import drumstory.drumstory.domain.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class AdminMemberDTO {
    @Data
    public static class AdminCreateMemberDTO {
        @Schema(description = "이름", example = "홍길동")
        private String name;
        @Schema(description = "전화번호", example = "01012345678")
        private String phoneNumber;
        @Schema(description = "회원ID", example = "0000")
        private String memberNum;
    }

    @Data
    public static class AddMemberResponse {
        @Schema(description = "회원ID", example = "0000")
        private String memberNum;
        @Schema(description = "이름", example = "홍길동")
        private String name;
        @Schema(description = "전화번호", example = "01012345678")
        private String phoneNumber;

        public AddMemberResponse(String memberNum, String name, String phoneNumber) {
            this.memberNum = memberNum;
            this.name = name;
            this.phoneNumber = phoneNumber;
        }
    }


    @Data
    public static class AdminUpdateMemberDTO {
        @Schema(description = "바꿀이름", example = "홍길동")
        private String name;
        @Schema(description = "바꿀전화번호", example = "01012345678")
        private String phoneNumber;
        @Schema(description = "바꿀회원ID", example = "1234")
        private String memberNum;
        @Schema(description = "기존회원ID", example = "0000")
        private String oldMemberNum;
    }

    @Data
    public static class UpdateMemberResponse {
        @Schema(description = "회원ID", example = "0000")
        private String memberNum;
        @Schema(description = "이름", example = "홍길동")
        private String name;
        @Schema(description = "전화번호", example = "01012345678")
        private String phoneNumber;

        public UpdateMemberResponse(String memberNum, String name, String phoneNumber) {
            this.memberNum = memberNum;
            this.name = name;
            this.phoneNumber = phoneNumber;
        }
    }

    @Data
    public static class DeleteMemberResponse {
        @Schema(description = "회원ID", example = "0000")
        private String memberNum;
        @Schema(description = "이름", example = "홍길동")
        private String name;
        @Schema(description = "전화번호", example = "01012345678")
        private String phoneNumber;

        public DeleteMemberResponse(String memberNum, String name, String phoneNumber) {
            this.memberNum = memberNum;
            this.name = name;
            this.phoneNumber = phoneNumber;
        }
    }

    @Data
    public static class AdminDeleteMemberDTO {
        @Schema(description = "회원ID", example = "1234")
        private String memberNum;
    }




}

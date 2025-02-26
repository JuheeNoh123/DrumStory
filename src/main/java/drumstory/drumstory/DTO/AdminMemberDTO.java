package drumstory.drumstory.DTO;

import lombok.Data;

public class AdminMemberDTO {
    @Data
    public static class AdminCreateMemberDTO {
        private String name;
        private String phoneNumber;
        private String memberId;
    }


}

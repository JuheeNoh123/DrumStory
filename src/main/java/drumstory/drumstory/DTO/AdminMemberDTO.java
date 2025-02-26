package drumstory.drumstory.DTO;

import lombok.Data;

public class AdminMemberDTO {

    @Data
    public static class AdminCreateMemberRequest{
        private String name;
        private String phoneNumber;
        private String memberNum;
    }
}

package drumstory.drumstory.controller;

import drumstory.drumstory.DTO.AdminMemberDTO;
import drumstory.drumstory.DTO.MemberDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminMemberController {


    @PostMapping("admin/member/add")
    public String addMember(@RequestBody AdminMemberDTO.AdminCreateMemberDTO request) {
        //service 멤버 추가
        return null;
    }
}

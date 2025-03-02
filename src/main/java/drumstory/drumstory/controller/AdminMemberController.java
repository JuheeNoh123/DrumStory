package drumstory.drumstory.controller;

import drumstory.drumstory.DTO.AdminMemberDTO;
import drumstory.drumstory.domain.Member;
import drumstory.drumstory.service.AdminMemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AdminMemberController {

    private final AdminMemberService adminMemberService;
    @PostMapping("/admin/member/add")
    public ResponseEntity<AdminMemberDTO.AddMemberResponse> addMember(@RequestBody AdminMemberDTO.AdminCreateMemberDTO request){
        Member member = adminMemberService.add(request.getName(), request.getPhoneNumber(), request.getMemberNum());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AdminMemberDTO.AddMemberResponse(member.getMemberNum(), member.getName(), member.getRole()));
    }

    @GetMapping("/admin/member")
    public List<Member> getAllMembers(){
        return adminMemberService.findAll();
    }
}

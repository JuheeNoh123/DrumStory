package drumstory.drumstory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.ROLE_MEMBER;

    @Id
    @GeneratedValue
    private long Id;
    private String name;
    private String phoneNumber;
    private String memberNum;

    public Member(String name, String phoneNumber, String memberNum) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.memberNum = memberNum;
    }



}

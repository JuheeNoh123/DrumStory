package drumstory.drumstory.service;

import drumstory.drumstory.domain.Member;
import drumstory.drumstory.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String memberNum) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberNum(memberNum);
        Set<GrantedAuthority> grantedAuthority = new HashSet<>();

        if (member != null) {
            if (member.getRole().name().equals("ROLE_ADMIN")) {
                grantedAuthority.add(new SimpleGrantedAuthority(member.getRole().name()));
                grantedAuthority.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
            } else {
                grantedAuthority.add(new SimpleGrantedAuthority(member.getRole().name()));
            }

            return new User(member.getMemberNum(), member.getName(), grantedAuthority);
        } else {
            throw new UsernameNotFoundException("회원번호를 찾을 수 없습니다." + memberNum);
        }
    }
}
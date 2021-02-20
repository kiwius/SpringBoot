package springSecurityFormLogin.service.serviceImpl;

import springSecurityFormLogin.entity.Member;
import springSecurityFormLogin.repository.MemberRepository;
import springSecurityFormLogin.role.Role;
import springSecurityFormLogin.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUserName(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(()->new UsernameNotFoundException(memberId));
        //입력받은 memberId를 통해 DB에서 member 정보를 가져옴

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if("admin".equals(memberId))
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        else
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));

        return new User(member.getMemberId(), member.getPassword(), grantedAuthorities);
        //UserDetail에 정의된 User 인스턴스에 가져온 Member 정보에 담아 반환
    }
}

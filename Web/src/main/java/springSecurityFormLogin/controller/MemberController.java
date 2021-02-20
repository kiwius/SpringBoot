package springSecurityFormLogin.controller;

import springSecurityFormLogin.entity.Member;
import springSecurityFormLogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

/**
 * 기본적으로 보여줄 페이지와 ADMIN 권한을 가진 유저만 접근 가능한 페이지, 새로운 멤버의 가입요청을 처리하는 Controller
 *
 * @author suyongHan
 * @version 1.0, 최초커밋
 */

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/kiwius")
    public String mainPage(@AuthenticationPrincipal User user, Map<String, Object> model) {
        List<Member> members = memberRepository.findAll();
        model.put("members", members);
        return "homepage";
    }

    @GetMapping("/kiwius/admin")
    public String adminPage(@AuthenticationPrincipal User user, Map<String, Object> model) {
        return "adminPage";
    }

    @GetMapping("/kiwius/member/new")
    public String memberJoinForm(Member memberForm) {
        return "memberJoinForm";
    }

    @PostMapping("/kiwius/member/new")
    public String memberJoin(Member memberForm) {
        memberForm.setPassword(passwordEncoder.encode(memberForm.getPassword())); //비밀번호 암호화
        memberRepository.save(memberForm);
        return "redirect:/login";
    }
}

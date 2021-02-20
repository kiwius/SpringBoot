package springSecurityFormLogin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springSecurityFormLogin.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // UserDatailsService에서 사용하기 위한 findByMemberId()메소드를 추가
    Optional<Member> findByMemberId(String memberId);
}

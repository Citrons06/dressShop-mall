package dressshop.repository.member;

import dressshop.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Member findByNickname(String nickname);
    Member findByEmail(String email);
}

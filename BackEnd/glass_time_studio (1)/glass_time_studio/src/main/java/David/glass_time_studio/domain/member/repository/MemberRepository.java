package David.glass_time_studio.domain.member.repository;

import David.glass_time_studio.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findMemberByEmail(String email);
}

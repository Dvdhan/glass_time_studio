package David.glass_time_studio.domain.member.service;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    public Member createMember(Member member){
        return null;
    }
    public Member findMemberByEmail(String email){
        return null;
    }
    public Member updateMember(Member member){
        return null;
    }
}

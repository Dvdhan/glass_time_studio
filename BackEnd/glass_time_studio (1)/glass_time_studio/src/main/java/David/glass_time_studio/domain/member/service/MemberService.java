package David.glass_time_studio.domain.member.service;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.domain.member.repository.MemberRepository;
import David.glass_time_studio.global.advice.BusinessLogicException;
import David.glass_time_studio.global.advice.ExceptionCode;
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
        Member createdMember = memberRepository.save(member);
        return createdMember;
    }
    public Member findMemberById (long memberId) {
        Member findMember = memberRepository.findById(memberId);
        if(findMember == null) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        return findMember;
    }
    public Member findMemberByEmail(String email){
        return memberRepository.findMemberByEmail(email);
    }
    public Member updateMember(Member member){
        return null;
    }
}

package David.glass_time_studio.domain.member.mapper;

import David.glass_time_studio.domain.member.dto.MemberDto;
import David.glass_time_studio.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMapperImpl {

    public MemberDto.MyPage memberToMyPage(Member member){
        MemberDto.MyPage myPage = new MemberDto.MyPage();

        myPage.setMemberId(member.getMemberId());
        myPage.setMemberName(member.getMemberName());
        myPage.setEmail(member.getEmail());
        return myPage;
    }
}

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

        myPage.setMember_Id(member.getMember_Id());
        myPage.setMember_Name(member.getMember_Name());
        myPage.setEmail(member.getEmail());
        return myPage;
    }
}

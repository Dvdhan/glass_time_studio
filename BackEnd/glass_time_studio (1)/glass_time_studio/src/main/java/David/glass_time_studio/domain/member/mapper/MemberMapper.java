package David.glass_time_studio.domain.member.mapper;

import David.glass_time_studio.domain.member.dto.MemberDto;
import David.glass_time_studio.domain.member.entity.Member;

public interface MemberMapper {
    Member memberDtoPatchToMember(MemberDto.Patch memberPatch);

    MemberDto.Response memberToMemberDtoResponse (Member member);
}

package David.glass_time_studio.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberDto {

    @Getter
    @Setter
    public static class Patch{
        private long memberId;
        private String memberName;
        private String email;
    }
    @Getter
    @Setter
    public static class Response{
        private long memberId;
        private String memberName;
        private String email;
    }

    @Getter
    @Setter
    public static class MyPage{
        private long memberId;
        private String memberName;
        private String email;
    }
}

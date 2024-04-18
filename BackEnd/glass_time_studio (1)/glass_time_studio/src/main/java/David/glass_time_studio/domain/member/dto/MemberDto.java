package David.glass_time_studio.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

public class MemberDto {

    @Getter
    @Setter
    public static class Patch{
        private long member_Id;
        private String member_Name;
        private String email;
    }
    @Getter
    @Setter
    public static class Response{
        private long member_Id;
        private String member_Name;
        private String email;
    }

    @Getter
    @Setter
    public static class MyPage{
        private long member_Id;
        private String member_Name;
        private String email;
    }
}

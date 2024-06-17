package David.glass_time_studio.domain.review.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public class ReviewDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post{
        Long memberId;
        Long bookingId;
        String title;
        String content;
        String lecture_Name;
        Long lecture_Id;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch{
        Long memberId;
        Long bookingId;
        String title;
        String content;
        String lecture_Name;
        Long lecture_Id;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        Long reviewId;
        Long memberId;
        Long bookingId;
        String lecture_Name;
        Long lecture_Id;
        String title;
        String content;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime modified_at;
    }
}

package David.glass_time_studio.domain.announcement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
public class AnnouncementDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Post{
        @NotBlank
        String announcement_Title;
        @NotBlank
        String announcement_Content;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Patch{
        String announcement_Title;
        String announcement_Content;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Response{
        Long announcement_Id;
        String announcement_Title;
        String announcement_Content;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime modified_at;
    }
}

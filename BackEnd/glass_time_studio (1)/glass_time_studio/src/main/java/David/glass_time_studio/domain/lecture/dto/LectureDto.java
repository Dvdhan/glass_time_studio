package David.glass_time_studio.domain.lecture.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class LectureDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Post{
        @NotBlank
        String lecture_Name;
        private String lecture_Period;
        private Long lecture_Price;
        private String lecture_Description;
        private String status;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Response{
        long lecture_Id;
        String lecture_Name;
        private String lecture_Period;
        private Long lecture_Price;
        private String lecture_Description;
        private String status;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Patch{
        String lecture_Name;
        private String lecture_Period;
        private Long lecture_Price;
        private String lecture_Description;
        private String status;
    }
}

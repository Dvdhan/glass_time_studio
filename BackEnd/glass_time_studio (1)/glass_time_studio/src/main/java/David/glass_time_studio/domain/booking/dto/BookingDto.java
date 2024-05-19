package David.glass_time_studio.domain.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
public class BookingDto {

    @AllArgsConstructor
    @Setter
    @Getter
    public static class Post{
        @NotNull
        Long lectureId;
        @NotNull
        LocalDate requestDate;
        @NotNull
        String requestTime;
        @NotNull
        String bookerName;
        @NotNull
        String mobile;
        @NotNull
        Long peopleNumber;
        String requestMessage;
        @NotNull
        private Long memberId;
    }
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Patch{
        Long lectureId;
        LocalDate requestDate;
        String requestTime;
        String bookerName;
        String mobile;
        Long peopleNumber;
        String requestMessage;
    }
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Response{
        Long bookingId;
        Long lecture_Id;
        String lectureName;
        LocalDate requestDate;
        String requestTime;
        String bookerName;
        String mobile;
        Long peopleNumber;
        String requestMessage;
        String status;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime modified_at;

    }
}

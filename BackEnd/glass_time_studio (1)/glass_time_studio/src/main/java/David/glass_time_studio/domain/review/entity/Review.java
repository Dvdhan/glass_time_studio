package David.glass_time_studio.domain.review.entity;

import David.glass_time_studio.global.auditable.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long reviewId;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private Long memberId;
    @Column
    private Long bookingId;
    @Column
    private String lecture_Name;
    @Column
    private Long lecture_Id;
}

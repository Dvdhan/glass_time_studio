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
    Long reviewId;
    @Column
    String title;
    @Column
    String content;
    @Column
    Long memberId;
    @Column
    Long bookingId;
    @Column
    String lecture_Name;
    @Column
    Long lecture_Id;
}

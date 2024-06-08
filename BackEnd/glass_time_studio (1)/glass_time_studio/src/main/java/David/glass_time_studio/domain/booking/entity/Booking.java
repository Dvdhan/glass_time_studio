package David.glass_time_studio.domain.booking.entity;

import David.glass_time_studio.domain.member.entity.Member;
import David.glass_time_studio.global.auditable.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Booking extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private Long bookingId;

    @Column(name = "lectureId")
    private Long lectureId;

    @Column
    private String lectureName;

    @Column
    private LocalDate requestDate;

    @Column
    private String requestTime;

    @Column
    private Long peopleNumber;

    @Column
    private String mobile;

    @Column
    private String bookerName;

    @Column(length = 100000)
    private String requestMessage;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column
    private String status;
}

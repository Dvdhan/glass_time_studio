package David.glass_time_studio.domain.announcement.entity;

import David.glass_time_studio.global.auditable.Auditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Announcement extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcement_Id;
    @Column
    private String announcement_Title;
    @Column(length = 100000)
    private String announcement_Content;
}

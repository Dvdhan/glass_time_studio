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
    @Column(name = "announcementId")
    private Long announcement_Id;
    @Column(name = "announcementTitle")
    private String announcement_Title;
    @Column(length = 100000, name = "announcementContent")
    private String announcement_Content;
}

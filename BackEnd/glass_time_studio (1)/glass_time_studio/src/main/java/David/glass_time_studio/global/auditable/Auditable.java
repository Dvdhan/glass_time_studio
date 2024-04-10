package David.glass_time_studio.global.auditable;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class Auditable {

    @CreatedDate
    @Column(name="created_At", updatable = false)
    private LocalDate created_At;

    @LastModifiedDate
    @Column(name="modified_At")
    private LocalDate modified_At;
}

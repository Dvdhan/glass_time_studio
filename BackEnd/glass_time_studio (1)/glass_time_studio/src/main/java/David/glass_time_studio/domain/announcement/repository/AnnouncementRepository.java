package David.glass_time_studio.domain.announcement.repository;

import David.glass_time_studio.domain.announcement.entity.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query(value = "SELECT * FROM ANNOUNCEMENT ORDER BY announcement_id", nativeQuery = true)
    public Page<Announcement> findAllAnnouncements(Pageable pageable);
}

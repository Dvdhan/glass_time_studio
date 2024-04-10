package David.glass_time_studio.domain.lecture.repository;

import David.glass_time_studio.domain.lecture.entity.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query(value = "SELECT * FROM LECTURE ORDER BY lecture_id", nativeQuery = true)
    public Page<Lecture> findAllLecture(Pageable pageable);
}

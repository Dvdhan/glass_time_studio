package David.glass_time_studio.domain.lecture.repository;

import David.glass_time_studio.domain.lecture.entity.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query(value = "SELECT * FROM LECTURE ORDER BY lecture_id", nativeQuery = true)
    public Page<Lecture> findAllLecture(Pageable pageable);

    @Query(value = "SELECT l FROM Lecture l WHERE l.status = 'Y'")
    public List<Lecture> findAvailableLectures();

    @Query(value = "SELECT * FROM LECTURE WHERE lecture_name LIKE :keyword", nativeQuery = true)
    public List<Lecture> searchLecture(@Param("keyword") String keyword);
}

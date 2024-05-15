package David.glass_time_studio.domain.lecture.service;

import David.glass_time_studio.domain.lecture.entity.Lecture;
import David.glass_time_studio.domain.lecture.repository.LectureRepository;
import David.glass_time_studio.global.advice.BusinessLogicException;
import David.glass_time_studio.global.advice.ExceptionCode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Getter
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    @Transactional
    public Lecture postLecture(Lecture lecture){
        Lecture savedLecture = lectureRepository.save(lecture);
        return savedLecture;
    }

    public Lecture findLecture(Long lecture_Id){
        Optional<Lecture> findLecture = lectureRepository.findById(lecture_Id);
        Lecture foundLecture = findLecture.orElseThrow(()->new BusinessLogicException(ExceptionCode.LECTURE_NOT_FOUND));
        return foundLecture;
    }

    public Page<Lecture> findAllLecture(int page, int size){
        return lectureRepository.findAllLecture(PageRequest.of(page, size));
    }

    public List<Lecture> getAllAvailableLectures() {
        return lectureRepository.findAvailableLectures();
    }

    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }

    public List<Lecture> searchLecturesByTitle(String keyword){
        return lectureRepository.searchLecture("%" + keyword + "%");
    }

    public Lecture updateLecture(Lecture lecture, Long lecture_Id){

        Lecture target = findLecture(lecture_Id);
        String new_Lecture_Name = lecture.getLecture_Name();

        boolean isUpdated = false;

        if(new_Lecture_Name != null){
            target.setLecture_Name(new_Lecture_Name);
            isUpdated = true;
        }

        if(isUpdated){
            return lectureRepository.save(target);
        }else{
            return target;
        }
    }
    public void deleteLecture (Lecture lecture){
        lectureRepository.delete(lecture);
    }


}

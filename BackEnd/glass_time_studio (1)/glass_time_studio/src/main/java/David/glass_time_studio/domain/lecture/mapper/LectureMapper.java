package David.glass_time_studio.domain.lecture.mapper;

import David.glass_time_studio.domain.lecture.dto.LectureDto;
import David.glass_time_studio.domain.lecture.entity.Lecture;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LectureMapper {
    Lecture lectureDtoPostToLecture (LectureDto.Post post);
    Lecture lectureDtoPatchToLecture (LectureDto.Patch patch);
    LectureDto.Response lectureToLectureDtoResponse (Lecture lecture);
    List<LectureDto.Response> lecturesToLectureDtoResponse (List<Lecture> lectures);
}

package David.glass_time_studio.domain.lecture.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lecture_Id;
    @Column
    private String lecture_Name;

    public Lecture(Long lecture_Id, String lecture_Name){
        this.lecture_Id=lecture_Id;
        this.lecture_Name=lecture_Name;
    }
}

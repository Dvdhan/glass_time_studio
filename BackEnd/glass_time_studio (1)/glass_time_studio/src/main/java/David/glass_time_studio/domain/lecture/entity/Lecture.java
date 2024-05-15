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
    @Column
    private String lecture_Period;
    @Column
    private Long lecture_Price;
    @Column
    private String lecture_Description;
    @Column
    private String status;

    public Lecture(Long lecture_Id, String lecture_Name, String lecture_Period, Long lecture_Price, String lecture_Description, String status){
        this.lecture_Id=lecture_Id;
        this.lecture_Name=lecture_Name;
        this.lecture_Period=lecture_Period;
        this.lecture_Price=lecture_Price;
        this.lecture_Description=lecture_Description;
        this.status=status;
    }
}

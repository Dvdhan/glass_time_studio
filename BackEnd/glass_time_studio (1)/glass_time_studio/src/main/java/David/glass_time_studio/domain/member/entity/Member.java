package David.glass_time_studio.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_Id;
    @Column
    private String member_Name;
    @Column
    private String email;
    @Column
    private Long mobile;
    @Column
    private Date birthday;
    @Column
    private Year birthyear;
    @Column
    private String oauthType;
    @ElementCollection
    private List<String> roles = new ArrayList<>();

    public Member(String email) {
        this.email = email;
    }
    public Member (String email, String member_Name) {
        this.email = email;
        this.member_Name = member_Name;
    }
}

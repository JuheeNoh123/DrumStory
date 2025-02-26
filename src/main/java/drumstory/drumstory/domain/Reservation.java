package drumstory.drumstory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static jakarta.persistence.FetchType.LAZY;
@Entity
@Getter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    private long Id;

    private LocalDate resDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;


}

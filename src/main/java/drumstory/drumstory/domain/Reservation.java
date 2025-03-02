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

    public Reservation(LocalDate resDate, LocalTime startTime, LocalTime endTime, Member member) {
        this.resDate = resDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.member = member;
    }

    // 엔티티가 처음 저장될 때 createTime을 설정
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createTime = now;
        this.updateTime = now;  // 처음 생성 시에는 updateTime도 동일하게 설정
    }

    // 엔티티가 업데이트될 때 updateTime을 설정
    @PreUpdate
    public void preUpdate() {
        this.updateTime = LocalDateTime.now();
    }
}

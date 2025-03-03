package drumstory.drumstory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity(name = "time_table")
@Getter
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TimeTable {
    @Id
    private Long id;
    private String timeTable; // 예시로 시간만 문자열로 저장한 경우

    // equals와 hashCode 구현
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TimeTable timeTable1 = (TimeTable) obj;
        return id.equals(timeTable1.id);  // id로 비교
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);  // id로 hashCode 생성
    }
}



package drumstory.drumstory.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "time_table")
@Getter
@NoArgsConstructor
public class TimeTable {
    @Id
    private int Id;
    private String timeTable;
}

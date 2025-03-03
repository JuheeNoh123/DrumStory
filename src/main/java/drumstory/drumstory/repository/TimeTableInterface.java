package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.domain.TimeTable;

import java.time.LocalDate;
import java.util.List;

public interface TimeTableInterface {

    List<TimeTable> findAllByIds(List<Long> ids);

    List<TimeTable> getAllTimeTables();


}

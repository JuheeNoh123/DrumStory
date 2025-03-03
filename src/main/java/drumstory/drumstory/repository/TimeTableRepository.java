package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.domain.TimeTable;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimeTableRepository implements TimeTableInterface{
    private final EntityManager em;

    @Override
    public List<TimeTable> findAllByIds(List<Long> ids) {
        List<TimeTable> timeTables = new ArrayList<>();
        for (Long id : ids) {
            TimeTable timeTable = em.createQuery("SELECT t FROM time_table t WHERE t.id = :id", TimeTable.class)
                    .setParameter("id", id)
                    .getSingleResult();
            System.out.println(timeTable.getId()+timeTable.getTimeTable());
            timeTables.add(timeTable);
        }
        return timeTables;

    }

    @Override
    public List<TimeTable> getAllTimeTables() {
        return em.createQuery("SELECT t FROM time_table t", TimeTable.class)
                .getResultList();
    }


}

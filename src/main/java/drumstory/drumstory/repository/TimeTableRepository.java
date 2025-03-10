package drumstory.drumstory.repository;

import drumstory.drumstory.domain.TimeTable;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimeTableRepository implements TimeTableInterface{
    private final EntityManager em;

    @Override
    public List<TimeTable> findAllByIds(List<Integer> ids) {
        List<TimeTable> timeTables = new ArrayList<>();
        for (Integer id : ids) {
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

    @Override
    public TimeTable findById(Integer id){
        return em.find(TimeTable.class, id);
    }

    @Override
    public TimeTable findByTime(String time){
        return em.createQuery("select t from time_table t where t.timeTable=:time", TimeTable.class)
                .setParameter("time", time)
                .getSingleResult();
    }

}

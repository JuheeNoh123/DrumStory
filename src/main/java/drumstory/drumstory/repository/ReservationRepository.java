package drumstory.drumstory.repository;

import drumstory.drumstory.domain.TimeTable;
import drumstory.drumstory.domain.Reservation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReservationRepository implements ReservationInterface{
    private final EntityManager em;


    @Override
    public List<Reservation> getAllReservations() {
        return em.createQuery("Select r from Reservation r", Reservation.class).getResultList();
    }

    @Override
    public void saveReservation(Reservation reservation) {
        em.persist(reservation);
    }

    @Override
    public TimeTable getTimeTableByTime(String reservationTime) {
        return em.createQuery("select t from time_table t where t.timeTable = :reservationTime", TimeTable.class)
                .setParameter("reservationTime", reservationTime)
                .getSingleResult();
    }

    @Override
    public List<Reservation> findAll() {
        return em.createQuery("SELECT m FROM Reservation m", Reservation.class)
                .getResultList();
    }

    @Override
    public List<Reservation> findByResDate(LocalDate resDate) {
        System.out.println("Looking for reservations on date: " + resDate);
        return em.createQuery("select r from Reservation r Where r.resDate = :resDate", Reservation.class)
                .setParameter("resDate",resDate)
                .getResultList();
    }


    // 날짜와 예약 시간에 맞는 예약을 조회하는 메서드
    @Override
    public List<Reservation> findReservationsByDateAndTimes(LocalDate resDate, List<TimeTable> timeTables) {
        String queryStr = "SELECT r FROM Reservation r WHERE r.resDate = :resDate";
        for (TimeTable timeTable : timeTables) {
            System.out.println(timeTable.getId()+ timeTable.getTimeTable());
        }
        if (timeTables.size() == 1) {
            // timeTables에 하나만 있을 경우
            queryStr += " AND r.time = :time0";
        } else if (timeTables.size() == 2) {
            // timeTables에 두 개가 있을 경우
            queryStr += " AND (r.time = :time0 OR r.time = :time1)";
        }

        var query = em.createQuery(queryStr, Reservation.class)
                .setParameter("resDate", resDate);

        // timeTables에 맞춰 매개변수 설정
        if (timeTables.size() == 1) {
            query.setParameter("time0", timeTables.getFirst());
        } else if (timeTables.size() == 2) {
            query.setParameter("time0", timeTables.get(0));
            query.setParameter("time1", timeTables.get(1));
        }

        return query.getResultList();
    }


}

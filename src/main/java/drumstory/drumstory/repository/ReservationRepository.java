package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.TimeTable;
import drumstory.drumstory.domain.Reservation;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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
    public List<Reservation> findAll(LocalDate resDate) {
        return em.createQuery("SELECT r FROM Reservation r where r.resDate=:resDate", Reservation.class)
                .setParameter("resDate", resDate)
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

    //해당 시간에 예약한 사람들 조회
    @Override
    public List<Member> findMembersByTime(TimeTable time){
        return em.createQuery("select r.member from Reservation r where r.time = :time", Member.class)
                .setParameter("time", time)
                .getResultList();
    }

    @Override
    public boolean checkNextReservation(TimeTable time, Member member){
        List<Reservation> result = em.createQuery(
                        "SELECT r FROM Reservation r WHERE r.time = :time AND r.member = :member", Reservation.class)
                .setParameter("time", time)
                .setParameter("member", member)
                .getResultList();  // 결과가 없으면 빈 리스트 반환

        return !result.isEmpty();  // 리스트가 비어있지 않으면 true 반환
    }

    @Override
    public void deletePastReservations(LocalDate currentDate, int timeTableId) {
        em.createQuery("DELETE FROM Reservation r WHERE r.resDate < :currentDate OR (r.resDate = :currentDate AND r.time.id < :timeTableId)")
                .setParameter("currentDate", currentDate)
                .setParameter("timeTableId", timeTableId)
                .executeUpdate();

    }


}

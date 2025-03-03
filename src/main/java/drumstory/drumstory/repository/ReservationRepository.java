package drumstory.drumstory.repository;

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
    public List<Reservation> findAll() {
        return em.createQuery("SELECT m FROM Reservation m", Reservation.class)
                .getResultList();
    }

    @Override
    public List<Reservation> findByResDate(LocalDate resDate) {
        return em.createQuery("select r from Reservation r Where r.resDate = :resDate", Reservation.class)
                .setParameter("resDate",resDate)
                .getResultList();
    }

    @Override
    public List<TimeTable> getAllTimeTables() {
        return em.createQuery("SELECT t FROM time_table t", TimeTable.class)
                .getResultList();
    }

}

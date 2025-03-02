package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.service.AvailableTime;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository implements ReservationInterface{
    private final EntityManager em;
    @Override
    public List<AvailableTime> getAvailableTimes() {
        return em.createQuery("Select a from AvailableTIme a", AvailableTime.class).getResultList();
    }

    @Override
    public List<Reservation> getAllReservations() {
        return em.createQuery("Select r from Reservation r", Reservation.class).getResultList();
    }
}

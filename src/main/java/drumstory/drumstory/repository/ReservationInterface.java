package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.service.AvailableTime;

import java.util.List;

public interface ReservationInterface {

    List<AvailableTime> getAvailableTimes();

    List<Reservation> getAllReservations();
}

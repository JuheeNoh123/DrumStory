package drumstory.drumstory.repository;

import drumstory.drumstory.domain.AvailableTime;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.service.TimeTable;

import java.util.List;

public interface ReservationInterface {

    List<AvailableTime> getAvailableTimes();

    List<Reservation> getAllReservations();

    Reservation saveReservation(Reservation reservation);
}

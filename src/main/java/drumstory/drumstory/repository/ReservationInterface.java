package drumstory.drumstory.repository;

import drumstory.drumstory.domain.TImeTable;
import drumstory.drumstory.domain.Reservation;

import java.util.List;

public interface ReservationInterface {



    List<TImeTable> getTimeTable();

    List<Reservation> getAllReservations();

    Reservation saveReservation(Reservation reservation);
}

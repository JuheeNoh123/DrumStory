package drumstory.drumstory.repository;


import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.domain.TimeTable;

import java.time.LocalDate;
import java.util.List;

public interface ReservationInterface {



    List<TimeTable> getTimeTable();

    List<Reservation> getAllReservations();

    Reservation saveReservation(Reservation reservation);

    TimeTable getTimeTableByTime(String reservationTime);

    List<Reservation> findAll(LocalDate resDate);
}

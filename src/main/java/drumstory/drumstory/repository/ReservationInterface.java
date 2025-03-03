package drumstory.drumstory.repository;


import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.domain.TimeTable;

import java.time.LocalDate;
import java.util.List;

public interface ReservationInterface {

    List<Reservation> getAllReservations();

    void saveReservation(Reservation reservation);

    TimeTable getTimeTableByTime(String reservationTime);

    List<Reservation> findAll();


    List<Reservation> findByResDate(LocalDate resDate);

    List<TimeTable> getAllTimeTables();
}

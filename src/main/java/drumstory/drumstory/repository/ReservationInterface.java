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

    // 날짜와 예약 시간에 맞는 예약을 조회하는 메서드
    List<Reservation> findReservationsByDateAndTimes(LocalDate resDate, List<TimeTable> timeTables);
}

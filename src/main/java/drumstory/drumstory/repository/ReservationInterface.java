package drumstory.drumstory.repository;


import drumstory.drumstory.domain.Member;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.domain.TimeTable;

import java.time.LocalDate;
import java.util.List;

public interface ReservationInterface {

    List<Reservation> getAllReservations();

    void saveReservation(Reservation reservation);

    List<Reservation> findReservationByMember(Member member);

    List<Reservation> findByResDate(LocalDate resDate);

    // 날짜와 예약 시간에 맞는 예약을 조회하는 메서드
    List<Reservation> findReservationsByDateAndTimes(LocalDate resDate, List<TimeTable> timeTables);

    //해당 시간에 예약한 사람들 조회
    List<Member> findMembersByTime(TimeTable time);

    boolean checkNextReservation(TimeTable time, Member member);

    TimeTable getTimeTableByTime(String reservationTime);

    List<Reservation> findAll(LocalDate resDate);

    void deletePastReservations(LocalDate currentDate, int timeTableId);

    boolean deleteReservationByMember(Member member);

    Reservation findById(long Id);

    void deleteReservationById(long Id);
}

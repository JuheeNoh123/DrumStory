package drumstory.drumstory.service;

import drumstory.drumstory.domain.AvailableTime;
import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.repository.ReservationInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class TimeTable {
    private final ReservationInterface reservationInterface;

    public List<TimeTable> getAvailableTimes(){
        LocalTime now = LocalTime.now();
        List<AvailableTime> availableTimes = reservationInterface.getAvailableTimes();
        List<Reservation> reservations = reservationInterface.getAllReservations();
        System.out.println(now);

        System.out.println("Available Times:");
        availableTimes.forEach(time -> System.out.println("id: " + time.getId() + ", times: " + time.getAva_time()));

        System.out.println("Reservations:");
        reservations.forEach(res -> System.out.println("id: " + res.getId() + ", user: " + res.getTime1() + ", EndTime: " + res.getTime2()));

        return null;

    }
}

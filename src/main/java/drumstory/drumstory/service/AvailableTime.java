package drumstory.drumstory.service;

import drumstory.drumstory.domain.Reservation;
import drumstory.drumstory.repository.ReservationInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class AvailableTime {
    private final ReservationInterface reservationInterface;

    public List<AvailableTime> getAvailableTimes(){
        LocalTime now = LocalTime.now();
        List<AvailableTime> availableTimes = reservationInterface.getAvailableTimes();
        List<Reservation> reservations = reservationInterface.getAllReservations();

        System.out.println(availableTimes);
        System.out.println(reservations);
        return null;

    }
}

package drumstory.drumstory.service;

import drumstory.drumstory.domain.TImeTable;
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


}

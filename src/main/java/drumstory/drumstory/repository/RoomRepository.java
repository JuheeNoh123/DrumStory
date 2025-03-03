package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Room;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomRepository implements RoomInterface {
    private final EntityManager em;

    @Override
    public Room findByRoomNum(String roomNum) {
        return em.find(Room.class, roomNum);
    }
}

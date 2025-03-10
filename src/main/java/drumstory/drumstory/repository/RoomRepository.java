package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Room;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RoomRepository implements RoomInterface {
    private final EntityManager em;

    @Override
    public Room findById(Long roomId) {
        return em.find(Room.class, roomId);
    }

    // 모든 방을 데이터베이스에서 조회하는 메서드
    @Override
    public List<Room> findAll() {
        return em.createQuery("SELECT r FROM Room r", Room.class)
                .getResultList();
    }
}

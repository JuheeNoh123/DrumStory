package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Room;

import java.util.List;

public interface RoomInterface {
    Room findByRoomNum(String roomNum);
    List<Room> findAll();
}

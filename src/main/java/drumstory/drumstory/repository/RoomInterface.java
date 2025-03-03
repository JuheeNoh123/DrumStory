package drumstory.drumstory.repository;

import drumstory.drumstory.domain.Room;

public interface RoomInterface {
    Room findByRoomNum(String roomNum);
}

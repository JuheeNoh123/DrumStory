package drumstory.drumstory.DTO;

import drumstory.drumstory.domain.Room;
import drumstory.drumstory.domain.TimeTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class ReservationDTO {
    @Data
    public static class ScheduleRes {
        @Schema(description = "예약 가능한 시간", example = "11:30")
        private String ava_time;
    }

    @Data
    public static class ReservationTimeReq {
        @Schema(description = "예약 시간 리스트", example = "[37,38]")
        private List<Integer> resTimeIds;
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        private String resDate;
    }

    @Data
    @AllArgsConstructor
    public static class ReservationTimeRes {
        @Schema(description = "예약자명", example = "노주희")
        private String name;
        @Schema(description = "선택한 예약 시간 리스트", example = "[{id:37,timetable:\"오후 06:30\"},{id:38,timetable:\"오후 07:00\"}]")
        private List<TimeTable> selectTimes;
        @Schema(description = "예약 시작 시간", example = "오전 HH:MM")
        private String startTime;
        @Schema(description = "예약 끝 시간", example = "오전 HH:MM")
        private String endTime;
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        private LocalDate resDate;
        @Schema(description = "예약요일", example = "Monday")
        private String resDay;


    }

    @Data
    public static class ReservationTimeRoomReq {
        @Schema(description = "예약 시간 리스트", example = "[37,38]")
        private List<Integer> resTimeIds;
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        private String resDate;
        @Schema(description = "예약한 방", example = "1")
        private Long roomId;
    }


    @Data
    @AllArgsConstructor
    public static class ReservationTimeRoomRes {
        @Schema(description = "예약자명", example = "노주희")
        private String name;
        @Schema(description = "예약 시작 시간", example = "오전 HH:MM")
        private String startTime;
        @Schema(description = "예약 끝 시간", example = "오전 HH:MM")
        private String endTime;
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        private LocalDate resDate;
        @Schema(description = "예약요일", example = "Monday")
        private String resDay;
        @Schema(description = "예약한 방", example = "1번 방")
        private String roomNum;
    }

    @Data
    public static class DateReq{
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        LocalDate resDate;
    }



    @Data
    @AllArgsConstructor
    public static class AvailableTimesAndMember{
        @Schema(description = "예약자", example = "노주희")
        String name;
        @Schema(description = "가능한 시간", example = "[{ \"timeTable\": \"02:00\",\"id\": 5 },..]")
        List<TimeTable> availableTimes;
    }

    @Data
    @AllArgsConstructor
    public static class AvailableRoomsRes{
        @Schema(description = "예약자", example = "노주희")
        String name;
        @Schema(description = "가능한 연습실", example = "{Id:1, roomNum:\"1번 방\"}")
        List<Room> availableRooms;
    }

    @Data
    @AllArgsConstructor
    public static class AvailableRoomsReq{
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        private LocalDate resDate;
        @Schema(description = "예약 시간 ID", example = "{1,2}")
        List<Integer> resTimeIds;
    }

    @Data
    public static class ReservationListReq {
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        private LocalDate resDate;
    }

    @Data
    public static class ReservationListRes {
        @Schema(description = "예약 시간 리스트", example = "[\"오후 HH:MM\"]")
        private String time;
        @Schema(description = "이름", example = "홍길동")
        private String name;
        @Schema(description = "예약한 방", example = "1")
        private String roomNum;
        @Schema(description = "id", example = "1")
        private long Id;

        public ReservationListRes(String time, String name, String roomNum, long Id) {
            this.time = time;
            this.name = name;
            this.roomNum = roomNum;
            this.Id = Id;
        }

    }


    @Data
    public static class DeleteReservationRes {
        @Schema(description = "예약 시간 리스트", example = "[\"오후 HH:MM\"]")
        private String time;
        @Schema(description = "이름", example = "홍길동")
        private String name;
        @Schema(description = "예약한 방", example = "1")
        private String roomNum;
        @Schema(description = "id", example = "1")
        private long Id;

        public DeleteReservationRes(String time, String name, String roomNum, long Id) {
            this.time = time;
            this.name = name;
            this.roomNum = roomNum;
            this.Id = Id;
        }
    }




}

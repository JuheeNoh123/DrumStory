package drumstory.drumstory.DTO;

import drumstory.drumstory.domain.TimeTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class ReservationDTO {

    @Data
    public static class ReservationTimeReq {
        @Schema(description = "예약 시간 리스트", example = "[ \"오전 HH:MM\",\"오후 HH:MM\"]")
        private List<String> times;
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        private String resDate;
    }

    @Data
    @AllArgsConstructor
    public static class ReservationTimeRes {
        @Schema(description = "예약자명", example = "노주희")
        private String name;
        @Schema(description = "선택한 예약 시간 리스트", example = "오전 HH:MM")
        private List<String> selectTimes;
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
        @Schema(description = "예약 시간 리스트", example = "[ \"오전 HH:MM\",\"오후 HH:MM\"]")
        private List<String> times;
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        private String resDate;
        @Schema(description = "예약한 방", example = "1")
        private String roomNum;
    }


    @Data
    @AllArgsConstructor
    public static class ReservationTimeRoomRes {
        @Schema(description = "예약자명", example = "노주희")
        private String name;
//        @Schema(description = "선택한 예약 시간 리스트", example = "오전 HH:MM")
//        private List<String> selectTimes;
        @Schema(description = "예약 시작 시간", example = "오전 HH:MM")
        private String startTime;
        @Schema(description = "예약 끝 시간", example = "오전 HH:MM")
        private String endTime;
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        private LocalDate resDate;
        @Schema(description = "예약요일", example = "Monday")
        private String resDay;
        @Schema(description = "예약한 방", example = "1")
        private String roomNum;
    }

    @Data
    public static class DateReq{
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        LocalDate date;
    }

    @Data
    @AllArgsConstructor
    public static class AvailableTimesAndMember{
        @Schema(description = "예약자", example = "노주희")
        String name;
        @Schema(description = "가능한 시간", example = "HH:mm (24시간제)")
        List<TimeTable> availableTimes;
    }
}

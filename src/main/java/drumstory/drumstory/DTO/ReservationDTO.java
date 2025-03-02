package drumstory.drumstory.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

public class ReservationDTO {
    @Data
    public static class ScheduleRes {
        @Schema(description = "예약 가능한 시간", example = "11:30")
        private String ava_time;
    }

    @Data
    public static class ReservationReq {
        @Schema(description = "예약 시간 리스트", example = "[ \"오전 HH:MM\",\"오후 HH:MM\"]")
        private List<String> times;
        @Schema(description = "예약날짜", example = "YYYY-MM-DD")
        private String resDate;
    }
}

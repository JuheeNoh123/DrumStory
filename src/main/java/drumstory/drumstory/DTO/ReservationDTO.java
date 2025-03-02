package drumstory.drumstory.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class ReservationDTO {
    @Data
    public static class ScheduleRes {
        @Schema(description = "예약 가능한 시간", example = "11:30")
        private String ava_time;
    }
}

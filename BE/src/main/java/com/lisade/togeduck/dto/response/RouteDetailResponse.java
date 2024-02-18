package com.lisade.togeduck.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteDetailResponse {

    Long id;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    LocalDateTime startedAt;
    String source;
    String destination;
    @JsonFormat(pattern = "HH:mm")
    LocalTime expectedAt;
    @JsonFormat(pattern = "HH:mm")
    LocalTime arrivalAt;
    Integer totalSeats;
    Integer reservedSeats;
    Integer cost;
}

package com.lisade.togeduck.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RouteDetailDto {

    Long id;
    LocalDateTime startedAt;
    String source;
    String destination;
    LocalTime departureAt;
    LocalTime arrivalAt;
    Long totalSeats;
    Long reservedSeats;
    Integer cost;
}

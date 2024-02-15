package com.lisade.togeduck.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RouteDetailDto {

    Long id;
    Long festivalId;
    LocalDateTime startedAt;
    String source;
    String destination;
    LocalTime expectedAt;
    Integer totalSeats;
    Long reservedSeats;
    Integer cost;

}

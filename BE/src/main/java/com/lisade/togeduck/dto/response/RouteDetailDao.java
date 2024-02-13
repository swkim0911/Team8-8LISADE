package com.lisade.togeduck.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RouteDetailDao {

    Long id;
    Long festivalId;
    LocalDateTime startedAt;
    String source;
    String destination;
    LocalTime arrivalAt;
    Integer totalSeats;
    Long reservedSeats;
    Integer cost;

}

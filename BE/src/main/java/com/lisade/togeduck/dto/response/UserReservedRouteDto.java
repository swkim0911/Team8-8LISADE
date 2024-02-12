package com.lisade.togeduck.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReservedRouteDto {

    private Long id;
    private String title;
    private LocalDate startedAt;
    private String location;
    private String stationName;
    private Integer price;
    private Integer totalSeats;
    private Integer reservedSeats;
    private String ImagePath;
}

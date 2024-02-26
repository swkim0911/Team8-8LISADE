package com.lisade.togeduck.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPageResponse {

    private String festivalName;
    private LocalDateTime startedAt;
    private String sourceCity;
    private String source;
    private LocalTime departureAt;
    private LocalTime arrivalAt;
    private String destinationCity;
    private String destination;
    private Integer numberOfSeats;
    private Integer numberOfReservedSeats;
    private Integer seatNo;
    private Integer price;
}

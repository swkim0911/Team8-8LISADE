package com.lisade.togeduck.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime startedAt;
    private String sourceCity;
    private String source;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureAt;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime arrivalAt;
    private String destinationCity;
    private String destination;
    private Integer numberOfSeats;
    private Integer numberOfReservedSeats;
    private Integer seatNo;
    private Integer price;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteAndStationInfo {

        private String sourceCity;
        private String source;
        private LocalDateTime departureAt;
        private LocalTime expectedTime;
        private Integer numberOfSeats;
        private Integer numberOfReservedSeats;
        private Integer price;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteAndFestivalInfo {

        private String festivalName;
        private LocalDateTime startedAt;
        private String destinationCity;
        private String destination;
    }
}

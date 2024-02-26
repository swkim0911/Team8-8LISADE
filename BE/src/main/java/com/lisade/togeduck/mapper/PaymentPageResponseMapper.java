package com.lisade.togeduck.mapper;

import static com.lisade.togeduck.dto.response.PaymentPageResponse.RouteAndFestivalInfo;
import static com.lisade.togeduck.dto.response.PaymentPageResponse.RouteAndStationInfo;

import com.lisade.togeduck.dto.response.PaymentPageResponse;
import java.time.LocalTime;

public class PaymentPageResponseMapper {


    public static PaymentPageResponse toPaymentPageResponse(Integer seatNo,
        RouteAndFestivalInfo routeAndFestivalInfo, RouteAndStationInfo routeAndStationInfo) {
        LocalTime expectedTime = routeAndStationInfo.getExpectedTime();
        return PaymentPageResponse.builder()
            .festivalName(routeAndFestivalInfo.getFestivalName())
            .startedAt(routeAndFestivalInfo.getStartedAt())
            .source(routeAndStationInfo.getSource())
            .sourceCity(routeAndStationInfo.getSourceCity())
            .departureAt(routeAndStationInfo.getDepartureAt().toLocalTime())
            .arrivalAt(
                routeAndStationInfo.getDepartureAt().toLocalTime().plusHours(expectedTime.getHour())
                    .plusMinutes(expectedTime.getMinute()))
            .destinationCity(routeAndFestivalInfo.getDestinationCity())
            .destination(routeAndFestivalInfo.getDestination())
            .numberOfSeats(routeAndStationInfo.getNumberOfSeats())
            .numberOfReservedSeats(routeAndStationInfo.getNumberOfReservedSeats())
            .seatNo(seatNo)
            .price(routeAndStationInfo.getPrice())
            .build();
    }
}

package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.RouteDetailDao;
import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.RouteRegistrationDto;
import com.lisade.togeduck.entity.Bus;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Station;
import com.lisade.togeduck.entity.enums.RouteStatus;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RouteMapper {

    public static Route toRoute(
        Festival festival,
        Bus bus,
        Station station,
        Integer distance,
        Integer price
    ) {
        // TODO expectedTime, startAt 설정하기

        return Route.builder()
            .bus(bus)
            .festival(festival)
            .price(price)
            .distance(distance)
            .station(station)
            .status(RouteStatus.PROGRESS)
            .expectedTime(LocalTime.now())
            .startedAt(LocalDateTime.now())
            .build();
    }

    public static RouteRegistrationDto toRouteRegistrationDto(Route route) {
        return RouteRegistrationDto.builder()
            .routeId(route.getId())
            .festivalId(route.getFestival().getId())
            .build();
    }

    public static RouteDetailDto toRouteDetailDto(RouteDetailDao route) {
        return RouteDetailDto.builder()
            .id(route.getId())
            .startedAt(route.getStartedAt())
            .source(route.getSource())
            .destination(route.getDestination())
            .departureAt(route.getArrivalAt().minusHours(4))
            .arrivalAt(route.getArrivalAt())
            .totalSeats(route.getTotalSeats())
            .reservedSeats(route.getReservedSeats())
            .cost(route.getCost())
            .build();
    }
}

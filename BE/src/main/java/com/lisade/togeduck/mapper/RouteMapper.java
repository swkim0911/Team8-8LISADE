package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.RouteRegistrationDto;
import com.lisade.togeduck.entity.Bus;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Route.RouteStatus;
import com.lisade.togeduck.entity.Station;

public class RouteMapper {

    public static Route toRoute(
        Festival festival,
        Bus bus,
        Station station,
        Integer price,
        Integer distance
    ) {
        return Route.builder()
            .bus(bus)
            .festival(festival)
            .price(price)
            .distance(distance)
            .station(station)
            .status(RouteStatus.PROGRESS)
            .build();
    }

    public static RouteRegistrationDto toRouteRegistrationDto(Route route) {
        return RouteRegistrationDto.builder()
            .routeId(route.getId())
            .festivalId(route.getFestival().getId())
            .build();
    }
}

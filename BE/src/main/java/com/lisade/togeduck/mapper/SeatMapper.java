package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.BusLayoutResponse;
import com.lisade.togeduck.dto.response.SeatListResponse;
import com.lisade.togeduck.dto.response.SeatResponse;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Seat;
import com.lisade.togeduck.entity.enums.SeatStatus;
import java.util.List;
import java.util.stream.Stream;

public class SeatMapper {

    public static SeatListResponse toSeatListResponse(BusLayoutResponse busLayoutResponse,
        List<SeatResponse> seats) {
        return SeatListResponse.builder()
            .numberOfSeats(busLayoutResponse.getNumberOfSeats())
            .row(busLayoutResponse.getRow())
            .col(busLayoutResponse.getCol())
            .backSeats(busLayoutResponse.getBackSeats())
            .seats(seats)
            .build();
    }

    public static List<Seat> toSeats(Route route, Integer numberOfSeats) {
        return Stream.iterate(1, no -> no + 1)
            .map((no) -> Seat.builder()
                .no(no)
                .route(route)
                .status(SeatStatus.AVAILABLE)
                .build())
            .limit(numberOfSeats)
            .toList();
    }
}

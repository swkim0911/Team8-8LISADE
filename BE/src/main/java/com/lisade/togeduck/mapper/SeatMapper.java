package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.SeatDto;
import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.entity.Seat;
import java.util.List;

public class SeatMapper {

    public static SeatDto toSeatDto(Seat seat) {
        return SeatDto.builder()
            .id(seat.getId())
            .seatNo(seat.getNo())
            .status(seat.getStatus().toString())
            .build();
    }

    public static SeatListDto toSeatListDto(List<Seat> seats) {
        return SeatListDto.builder()
            .numberOfSeats(seats.size())
            .seats(seats.stream()
                .map(SeatMapper::toSeatDto)
                .toList())
            .build();
    }
}

package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.entity.Seat;
import com.lisade.togeduck.mapper.SeatMapper;
import com.lisade.togeduck.repository.SeatRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatListDto getList(Long routeId) {
        List<Seat> seats = seatRepository.findAllByRouteId(routeId);

        return SeatMapper.toSeatListDto(seats);
    }
}

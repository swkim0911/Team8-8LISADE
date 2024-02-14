package com.lisade.togeduck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.lisade.togeduck.dto.request.SeatRegistrationDto;
import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Seat;
import com.lisade.togeduck.entity.UserRoute;
import com.lisade.togeduck.entity.enums.RouteStatus;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.lisade.togeduck.repository.SeatRepository;
import com.lisade.togeduck.repository.UserRouteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @InjectMocks
    private SeatService seatService;
    @Mock
    private RouteService routeService;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private UserRouteRepository userRouteRepository;

    @Test
    @DisplayName("특정 노선에 대한 좌석 상태 조회 성공 테스트")
    void getSeatsOfRouteTest() {
        // given
        List<Seat> response = seatsResponse();

        doReturn(response)
            .when(seatRepository).findAllByRouteId(any(Long.class));

        // when
        SeatListDto seatListDto = seatService.getList(1L);

        // then
        assertEquals(5, seatListDto.getNumberOfSeats());

        verify(seatRepository, times(1)).findAllByRouteId(any(Long.class));
    }

    @Test
    @DisplayName("좌석 등록 성공 테스트")
    void registerSeatTest() {
        // given
        Long routeId = 1L;
        Integer no = 1;

        SeatRegistrationDto request = SeatRegistrationDto.builder()
            .no(no)
            .build();

        Route route = Route.builder()
            .id(routeId)
            .status(RouteStatus.PROGRESS)
            .build();

        Seat seat = Seat.builder()
            .no(no)
            .status(SeatStatus.AVAILABLE)
            .build();

        doReturn(route).when(routeService)
            .get(routeId);

        doReturn(Optional.of(seat)).when(seatRepository)
            .findByRouteIdAndNo(routeId, no);

        // when
        seatService.register(routeId, request);

        // then
        verify(seatRepository, times(1))
            .findByRouteIdAndNo(any(Long.class), any(Integer.class));
        verify(userRouteRepository, times(1))
            .save(any(UserRoute.class));
    }

    private List<Seat> seatsResponse() {
        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            seats.add(Seat.builder()
                .id((long) i)
                .no(i + 1)
                .status(SeatStatus.AVAILABLE)
                .build());
        }

        return seats;
    }
}
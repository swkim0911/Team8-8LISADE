package com.lisade.togeduck.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.entity.Seat;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.lisade.togeduck.repository.SeatRepository;
import java.util.ArrayList;
import java.util.List;
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
    private SeatRepository seatRepository;

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
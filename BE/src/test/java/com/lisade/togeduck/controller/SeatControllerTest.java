package com.lisade.togeduck.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lisade.togeduck.dto.request.SeatRegistrationDto;
import com.lisade.togeduck.dto.response.SeatDto;
import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.lisade.togeduck.service.SeatService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(SeatController.class)
@ExtendWith(MockitoExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
class SeatControllerTest {

    @MockBean
    private SeatService seatService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("특정 노선에 대한 남은 좌석을 조회 성공 테스트")
    public void getSeatsOfRouteTest() throws Exception {
        // given
        Long festivalId = 1L;
        Long routeId = 1L;

        doReturn(seatsResponse()).when(seatService)
            .getList(any(Long.class));

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.get("/festivals/{festival_id}/routes/{route_id}/seats",
                festivalId, routeId)
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        SeatListDto response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
            SeatListDto.class);

        assertEquals(5, response.getNumberOfSeats());
    }

    @Test
    @DisplayName("좌석 등록 성공 테스트")
    void registerSeatTest() throws Exception {
        // given
        Long festivalId = 1L;
        Long routeId = 1L;
        SeatRegistrationDto request = SeatRegistrationDto.builder()
            .no(1)
            .build();

        doNothing().when(seatService)
            .register(any(Long.class), any(SeatRegistrationDto.class));

        // when
        ResultActions resultActions = mockMvc.perform(
            MockMvcRequestBuilders.patch("/festivals/{festival_id}/routes/{route_id}/seats",
                    festivalId, routeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request))
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk())
            .andReturn();
    }

    private SeatListDto seatsResponse() {
        List<SeatDto> seats = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            seats.add(SeatDto.builder()
                .id((long) i)
                .seatNo(i + 1)
                .status(SeatStatus.AVAILABLE.toString())
                .build());
        }

        return SeatListDto.builder()
            .numberOfSeats(seats.size())
            .seats(seats)
            .build();
    }
}
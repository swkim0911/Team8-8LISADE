package com.lisade.togeduck.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.lisade.togeduck.dto.response.TMapResultDto;
import com.lisade.togeduck.repository.CityRepository;
import com.lisade.togeduck.repository.StationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

class LocationServiceTest {

    private LocationService locationService;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private StationRepository stationRepository;
    @Mock
    private FestivalService festivalService;

    @BeforeEach
    void setUp() {
        RestTemplate restTemplate = new RestTemplate();
        locationService = new LocationService(restTemplate, cityRepository, stationRepository,
            festivalService);
    }

    @Test
    @DisplayName("TMap으로 요청을 했을 때 정상적으로 응답이 오는지 테스트")
    void getTMapResponseTest() {
        // given
        Double startX = 126.982177;
        Double startY = 37.564686;
        Double endX = 129.032714;
        Double endY = 35.106811;

        // when
        TMapResultDto tMapResultDto = locationService.getDistance(startX, startY, endX, endY);

        // then
        assertNotNull(tMapResultDto);
    }
}
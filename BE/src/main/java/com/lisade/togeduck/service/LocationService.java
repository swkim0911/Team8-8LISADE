package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.DistancePricesDto;
import com.lisade.togeduck.dto.response.LocationListDto;
import com.lisade.togeduck.dto.response.TMapResultDto;
import com.lisade.togeduck.entity.City;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.Station;
import com.lisade.togeduck.exception.StationNotFoundException;
import com.lisade.togeduck.mapper.LocationMapper;
import com.lisade.togeduck.repository.CityRepository;
import com.lisade.togeduck.repository.StationRepository;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

    @Setter
    @Value(("${T_MAP_URL}"))
    private String T_MAP_URL;
    @Setter
    @Value("${T_MAP_APP_KEY}")
    private String T_MAP_APP_KEY;

    private final RestTemplate restTemplate;
    private final CityRepository cityRepository;
    private final StationRepository stationRepository;
    private final FestivalService festivalService;

    public LocationListDto getList() {
        List<City> cities = getCities();

        return LocationMapper.toLocationListDto(cities);
    }

    public List<City> getCities() {
        return cityRepository.findAllWithStation();
    }

    public Station getStation(Long stationId) {
        return stationRepository.findById(stationId).orElseThrow(StationNotFoundException::new);
    }

    public DistancePricesDto getDistance(Long stationId, Long festivalId) {
        Station station = getStation(stationId);
        Festival festival = festivalService.get(festivalId);

        TMapResultDto tMapResultDto = requestTMap(station.getXPos(), station.getYPos(),
            festival.getXPos(), festival.getYPos());

        return null;
    }

    private TMapResultDto requestTMap(Double startX, Double startY, Double endX, Double endY) {
        UriComponents uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(
                T_MAP_URL)
            .queryParam("version", 1)
            .encode(StandardCharsets.UTF_8)
            .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.set("appKey", T_MAP_APP_KEY);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("totalValue", 2);
        body.add("startX", startX);
        body.add("startY", startY);
        body.add("endX", endX);
        body.add("endY", endY);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body,
            httpHeaders);

        ResponseEntity<TMapResultDto> response = restTemplate.postForEntity(
            uriComponentsBuilder.toUri(),
            httpEntity,
            TMapResultDto.class);

        return response.getBody();
    }
}

package com.lisade.togeduck.service;

import com.lisade.togeduck.cache.service.LocationCacheService;
import com.lisade.togeduck.cache.value.LocationCacheValue;
import com.lisade.togeduck.dto.response.DistancePricesResponse;
import com.lisade.togeduck.dto.response.DistancePricesResponse.BusInfo;
import com.lisade.togeduck.dto.response.LocationListDto;
import com.lisade.togeduck.dto.response.TMapResultResponse;
import com.lisade.togeduck.dto.response.TMapResultResponse.Properties;
import com.lisade.togeduck.entity.City;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.Station;
import com.lisade.togeduck.exception.StationNotFoundException;
import com.lisade.togeduck.mapper.LocationMapper;
import com.lisade.togeduck.repository.CityRepository;
import com.lisade.togeduck.repository.StationRepository;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
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

    private static final String T_MAP_APP_KEY_HEADER_NAME = "appKey";
    private static final String T_MAP_PARAMETER_VERSION = "version";
    private static final String T_MAP_BODY_TOTAL_VALUE = "totalValue";
    private static final String T_MAP_BODY_START_X = "startX";
    private static final String T_MAP_BODY_START_Y = "startY";
    private static final String T_MAP_BODY_END_X = "endX";
    private static final String T_MAP_BODY_END_Y = "endY";

    @Setter
    @Value(("${T_MAP_URL}"))
    private String T_MAP_URL;
    @Setter
    @Value("${T_MAP_APP_KEY}")
    private String T_MAP_APP_KEY;

    private final RestTemplate restTemplate;
    private final CityRepository cityRepository;
    private final StationRepository stationRepository;
    private final LocationCacheService locationCacheService;
    private final FestivalService festivalService;
    private final BusService busService;

    public LocationListDto getList() {
        List<City> cities = getCities();

        return LocationMapper.toLocationListResponse(cities);
    }

    public List<City> getCities() {
        return cityRepository.findAllWithStation();
    }

    public Station getStation(Long stationId) {
        return stationRepository.findById(stationId).orElseThrow(StationNotFoundException::new);
    }

    public DistancePricesResponse getDistance(Long stationId, Long festivalId) {
        String locationId = stationId + "," + festivalId;

        Optional<LocationCacheValue> locationCacheValue = locationCacheService.get(locationId);

        if (locationCacheValue.isPresent()) {
            return locationCacheValue.get().getDistancePricesResponse();
        }

        Station station = getStation(stationId);
        Festival festival = festivalService.get(festivalId);

        TMapResultResponse tMapResultResponse = requestTMap(station.getXPos(), station.getYPos(),
            festival.getXPos(), festival.getYPos());

        Properties properties = tMapResultResponse.getFeatures().get(0).getProperties();

        Integer distance = properties.getTotalDistance() / 1000;
        Integer expectedTime = properties.getTotalTime();

        List<BusInfo> busInfos = busService.getBusInfo(distance);

        DistancePricesResponse distancePricesResponse = LocationMapper.toDistancePricesResponse(
            busInfos, distance, expectedTime);

        locationCacheService.save(LocationCacheValue.of(locationId, distancePricesResponse));

        return distancePricesResponse;
    }

    private TMapResultResponse requestTMap(Double startX, Double startY, Double endX, Double endY) {
        UriComponents uriComponentsBuilder = makeTMapUri();
        HttpEntity<?> httpEntity = makeTMapHttpRequest(startX, startY, endX, endY);

        ResponseEntity<TMapResultResponse> response = restTemplate.postForEntity(
            uriComponentsBuilder.toUri(),
            httpEntity,
            TMapResultResponse.class);

        return response.getBody();
    }

    private UriComponents makeTMapUri() {
        return UriComponentsBuilder.fromHttpUrl(
                T_MAP_URL)
            .queryParam(T_MAP_PARAMETER_VERSION, 1)
            .encode(StandardCharsets.UTF_8)
            .build();
    }

    private HttpEntity<?> makeTMapHttpRequest(Double startX, Double startY, Double endX,
        Double endY) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        httpHeaders.set(T_MAP_APP_KEY_HEADER_NAME, T_MAP_APP_KEY);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add(T_MAP_BODY_TOTAL_VALUE, 2);
        body.add(T_MAP_BODY_START_X, startX);
        body.add(T_MAP_BODY_START_Y, startY);
        body.add(T_MAP_BODY_END_X, endX);
        body.add(T_MAP_BODY_END_Y, endY);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(body,
            httpHeaders);

        return httpEntity;
    }
}

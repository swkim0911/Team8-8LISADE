package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.LocationListDto;
import com.lisade.togeduck.entity.City;
import com.lisade.togeduck.entity.Station;
import com.lisade.togeduck.exception.StationNotFoundException;
import com.lisade.togeduck.mapper.LocationMapper;
import com.lisade.togeduck.repository.CityRepository;
import com.lisade.togeduck.repository.StationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final CityRepository cityRepository;
    private final StationRepository stationRepository;

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
}

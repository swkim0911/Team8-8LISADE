package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.LocationListDto;
import com.lisade.togeduck.entity.City;
import com.lisade.togeduck.mapper.LocationMapper;
import com.lisade.togeduck.repository.CityRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final CityRepository cityRepository;

    public LocationListDto getList() {
        List<City> cities = getCities();

        return LocationMapper.toLocationListDto(cities);
    }

    public List<City> getCities() {
        return cityRepository.findAllWithStation();
    }
}

package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.LocationDto;
import com.lisade.togeduck.dto.response.LocationListDto;
import com.lisade.togeduck.dto.response.StationDto;
import com.lisade.togeduck.entity.City;
import com.lisade.togeduck.entity.Station;
import java.util.List;

public class LocationMapper {

    public static StationDto toStationDto(Station station) {
        return StationDto.builder()
            .id(station.getId())
            .station(station.getName())
            .build();
    }

    public static LocationDto toLocationDto(City city, List<Station> stations) {
        return LocationDto.builder()
            .id(city.getId())
            .city(city.getName())
            .stations(
                stations
                    .stream()
                    .map(LocationMapper::toStationDto)
                    .toList()
            )
            .build();
    }

    public static LocationListDto toLocationListDto(List<City> cities) {
        return LocationListDto.builder()
            .numberOfCities(cities.size())
            .locations(
                cities
                    .stream()
                    .map((city) -> toLocationDto(city, city.getStations()))
                    .toList()
            )
            .build();
    }
}

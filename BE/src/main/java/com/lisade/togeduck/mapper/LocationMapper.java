package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.DistancePricesResponse;
import com.lisade.togeduck.dto.response.DistancePricesResponse.BusInfo;
import com.lisade.togeduck.dto.response.LocationListDto;
import com.lisade.togeduck.dto.response.LocationResponse;
import com.lisade.togeduck.dto.response.StationResponse;
import com.lisade.togeduck.entity.City;
import com.lisade.togeduck.entity.Station;
import java.util.List;

public class LocationMapper {

    public static StationResponse toStationResponse(Station station) {
        return StationResponse.builder()
            .id(station.getId())
            .station(station.getName())
            .build();
    }

    public static LocationResponse toLocationResponse(City city, List<Station> stations) {
        return LocationResponse.builder()
            .id(city.getId())
            .city(city.getName())
            .stations(
                stations
                    .stream()
                    .map(LocationMapper::toStationResponse)
                    .toList()
            )
            .build();
    }

    public static LocationListDto toLocationListResponse(List<City> cities) {
        return LocationListDto.builder()
            .numberOfCities(cities.size())
            .locations(
                cities
                    .stream()
                    .map((city) -> toLocationResponse(city, city.getStations()))
                    .toList()
            )
            .build();
    }

    public static DistancePricesResponse toDistancePricesResponse(List<BusInfo> busInfos,
        Integer distance,
        Integer expectedTime) {
        return DistancePricesResponse.builder()
            .busInfos(busInfos)
            .distance(distance)
            .expectedTime(expectedTime)
            .build();
    }
}

package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.request.RouteRegistrationDto;
import com.lisade.togeduck.entity.Bus;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.PriceTable;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.Station;
import com.lisade.togeduck.exception.BusNotFoundException;
import com.lisade.togeduck.exception.RouteAlreadyExistsException;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.mapper.RouteMapper;
import com.lisade.togeduck.mapper.SeatMapper;
import com.lisade.togeduck.repository.BusRepository;
import com.lisade.togeduck.repository.RouteRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final FestivalService festivalService;
    private final SeatService seatService;
    private final LocationService locationService;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;

    @Transactional
    public com.lisade.togeduck.dto.response.RouteRegistrationDto save(Long festivalId,
        RouteRegistrationDto routeRegistration) {
        if (exists(festivalId, routeRegistration.getStationId())) {
            throw new RouteAlreadyExistsException();
        }

        Festival festival = festivalService.get(festivalId);
        Station station = locationService.getStation(routeRegistration.getStationId());
        Bus bus = getBus(routeRegistration.getBusId());

        List<PriceTable> priceTables = bus.getPriceTables();

        int price = 0;
        int maxDistance = 0;

        for (PriceTable priceTable : priceTables) {
            if (priceTable.getDistance() < routeRegistration.getDistance()
                && priceTable.getDistance() > maxDistance) {
                price = priceTable.getPrice() / bus.getNumberOfSeats() / 100 * 100;
            }
        }

        Route route = routeRepository.save(RouteMapper.toRoute(festival, bus, station,
            routeRegistration.getDistance(), price));

        seatService.saveAll(SeatMapper.toSeats(route, bus.getNumberOfSeats()));

        return RouteMapper.toRouteRegistrationDto(route);
    }

    public Route get(Long routeId) {
        return routeRepository.findById(routeId).orElseThrow(RouteNotFoundException::new);
    }

    public Bus getBus(Long busId) {
        return busRepository.findBusByIdWithPriceTable(busId)
            .orElseThrow(BusNotFoundException::new);
    }

    public boolean exists(Long festivalId, Long stationId) {
        return routeRepository.existsByFestivalIdAndStationId(festivalId, stationId);
    }
}

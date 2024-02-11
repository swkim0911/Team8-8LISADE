package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {

    boolean existsByFestivalIdAndStationId(Long festivalId, Long stationId);
}

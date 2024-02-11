package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Seat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByRouteId(Long routeId);
}

package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.Seat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SeatRepository extends JpaRepository<Seat, Long>, SeatRepositoryCustom {

    @Query("select s from Seat as s join fetch s.route where s.id =:routeId and s.no =:no")
    Optional<Seat> findByRouteIdAndNoWithRoute(Long routeId, Integer no);

    Optional<Seat> findByUserIdAndRouteId(Long userId, Long routeId);
}

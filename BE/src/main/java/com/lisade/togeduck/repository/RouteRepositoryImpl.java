package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QBus.bus;
import static com.lisade.togeduck.entity.QRoute.route;
import static com.lisade.togeduck.entity.QSeat.seat;

import com.lisade.togeduck.dto.response.RouteDetailDao;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RouteDetailDao> findRouteDetail(Long routeId) {
        RouteDetailDao routeDetailDao = queryFactory.select(Projections.constructor(
                RouteDetailDao.class,
                route.id,
                route.festival.id,
                route.startedAt,
                route.station.name,
                route.festival.location,
                route.expectedTime,
                getTotalSeats(routeId),
                getReservationSeats(routeId),
                route.price
            )).from(route)
            .leftJoin(route.station)
            .leftJoin(route.festival)
            .where(route.id.eq(routeId))
            .fetchOne();
        return Optional.ofNullable(routeDetailDao);
    }

    private Expression<Integer> getTotalSeats(Long routeId) {
        return ExpressionUtils.as(
            JPAExpressions.select(bus.numberOfSeats).from(bus).where(bus.eq(route.bus)),
            "totalSeats");
    }

    private Expression<Long> getReservationSeats(Long routeId) {
        return ExpressionUtils.as(JPAExpressions.select(seat.id.count())
            .from(seat)
            .where(seat.route.id.eq(routeId)
                .and(seat.status.eq(SeatStatus.RESERVATION))), "reservedSeats");
    }
}

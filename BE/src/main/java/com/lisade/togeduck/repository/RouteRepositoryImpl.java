package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QBus.bus;
import static com.lisade.togeduck.entity.QFestival.festival;
import static com.lisade.togeduck.entity.QRoute.route;
import static com.lisade.togeduck.entity.QSeat.seat;
import static com.lisade.togeduck.entity.QUser.user;
import static com.lisade.togeduck.entity.QUserRoute.userRoute;

import com.lisade.togeduck.dto.response.RouteDetailDao;
import com.lisade.togeduck.dto.response.UserReservedRouteDto;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @Override
    public Slice<UserReservedRouteDto> findReservedRoutes(Pageable pageable, Long userId) {
        List<UserReservedRouteDto> userReservedRoutes = queryFactory.select(Projections.constructor(
                UserReservedRouteDto.class,
                route.id,
                route.festival.title,
                route.startedAt,
                route.festival.location,
                route.station.name,
                route.price,
                route.bus.numberOfSeats,
                getTotalSeats(Long.valueOf(route.id.toString())),
                getFestivalImagePath(Long.valueOf(route.id.toString()))))
            .from(route)
            .join(route.station)
            .join(route.festival)
            .join(route.bus)
            .where(route.id.eq((getRouteId(userId))))
            .fetch();
        return null;
    }

    private Expression<?> getFestivalImagePath(Long routeId) {
        return Expressions.as(
            JPAExpressions.select(festival.festivalImages.get(0))
                .from(route)
                .join(route.festival.festivalImages)
                .where(route.id.eq(routeId)),
            "imagePath"
        );
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

    public Expression<Long> getRouteId(Long userId) {
        return Expressions.as(
            JPAExpressions.select(userRoute.route.id)
                .from(user)
                .join(user.userRoutes)
                .where(user.id.eq(userId)),
            "routeId"
        );
    }
}

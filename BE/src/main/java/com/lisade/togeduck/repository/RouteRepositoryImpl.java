package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QBus.bus;
import static com.lisade.togeduck.entity.QCity.city;
import static com.lisade.togeduck.entity.QDriver.driver;
import static com.lisade.togeduck.entity.QFestival.festival;
import static com.lisade.togeduck.entity.QRoute.route;
import static com.lisade.togeduck.entity.QSeat.seat;
import static com.lisade.togeduck.entity.QStation.station;
import static com.lisade.togeduck.entity.QUser.user;

import com.lisade.togeduck.dto.response.FestivalRoutesResponse;
import com.lisade.togeduck.dto.response.RouteDetailDto;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.BusInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.DriverInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.RouteAndFestivalInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.SeatInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.StationInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteResponse;
import com.lisade.togeduck.entity.enums.RouteStatus;
import com.lisade.togeduck.entity.enums.SeatStatus;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RouteRepositoryImpl implements RouteRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RouteDetailDto> findRouteDetail(Long routeId, Long festivalId) {
        RouteDetailDto routeDetailDto = queryFactory.select(Projections.constructor(
                RouteDetailDto.class,
                route.id,
                route.startedAt,
                route.station.name,
                route.festival.location,
                route.expectedTime,
                getTotalSeats(routeId),
                getReservationSeats(routeId),
                route.price
            )).from(route)
            .join(route.station)
            .join(route.festival)
            .where(route.id.eq(routeId).and(festival.id.eq(festivalId)))
            .fetchOne();
        return Optional.ofNullable(routeDetailDto);
    }

    @Override
    public Slice<FestivalRoutesResponse> findRoutes(Pageable pageable, Long festivalId,
        String cityName) {

        JPAQuery<FestivalRoutesResponse> query = queryFactory.select(Projections.constructor(
                FestivalRoutesResponse.class,
                route.id,
                route.startedAt,
                station.name,
                route.price,
                route.status,
                bus.numberOfSeats,
                ExpressionUtils.as(getReservationSeats(), "reservedSeats")))
            .from(route)
            .join(station)
            .on(route.station.eq(station))
            .join(bus)
            .on(route.bus.eq(bus))
            .where(
                route.festival.id.eq(festivalId).and(route.status.eq(RouteStatus.RECRUIT)));

        if (!"all".equals(cityName)) { // cityName 조건
            query = query.where(station.city.id.eq(JPAExpressions.select(city.id)
                .from(city)
                .where(city.name.eq(cityName))));
        }

        Sort sort = pageable.getSort();

        Order createdAt = sort.getOrderFor("createdAt");
        if (createdAt != null) {
            query.orderBy(route.createdAt.desc());
        }

        Order population = sort.getOrderFor("reservedSeats");
        if (population != null) {
            StringPath aliasQuantity = Expressions.stringPath("reservedSeats");
            query.orderBy(aliasQuantity.desc());
        }

        List<FestivalRoutesResponse> festivalRoutes = query.limit(pageable.getPageSize() + 1)
            .fetch();

        boolean hasNext = false;
        if (festivalRoutes.size() > pageable.getPageSize()) {
            festivalRoutes.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(festivalRoutes, pageable, hasNext);
    }

    @Override
    public Slice<UserReservedRouteResponse> findReservedRoutes(Pageable pageable, Long userId) {
        JPAQuery<UserReservedRouteResponse> query = queryFactory.select(Projections.constructor(
                UserReservedRouteResponse.class,
                route.id,
                festival.title,
                route.startedAt,
                festival.location,
                station.name,
                route.price,
                route.status,
                bus.numberOfSeats,
                getReservationSeats(),
                festival.thumbnailPath))
            .from(route)
            .join(station)
            .on(route.station.eq(station))
            .join(festival)
            .on(festival.eq(route.festival))
            .join(bus)
            .on(route.bus.eq(bus))
            .where(route.id.in((getRouteId(userId))));

        Sort sort = pageable.getSort();
        Sort.Order order = sort.getOrderFor("createdAt");
        if (order != null) {
            query.orderBy(route.createdAt.desc());
        }

        List<UserReservedRouteResponse> userReservedRoutes = query.limit(
                pageable.getPageSize() + 1) // 다음 페이지 있는지 확인
            .fetch();

        boolean hasNext = false;
        if (userReservedRoutes.size() > pageable.getPageSize()) {
            userReservedRoutes.remove(pageable.getPageSize()); //한개 더 가져왔으니 더 가져온 데이터 삭제
            hasNext = true;
        }
        return new SliceImpl<>(userReservedRoutes, pageable, hasNext);
    }

    @Override
    public Optional<RouteAndFestivalInfo> findRouteAndFestivalInfo(Long routeId) {
        RouteAndFestivalInfo routeAndFestivalInfo = queryFactory.select(Projections.constructor(
                RouteAndFestivalInfo.class,
                festival.id,
                festival.title,
                route.expectedTime,
                route.status,
                route.price,
                route.startedAt,
                festival.location,
                festival.city.name,
                festival.thumbnailPath))
            .from(route)
            .join(festival)
            .on(route.festival.eq(festival))
            .join(city)
            .on(festival.city.eq(city))
            .where(route.id.eq(routeId))
            .fetchOne();
        return Optional.ofNullable(routeAndFestivalInfo);
    }

    @Override
    public Optional<StationInfo> findStationInfo(Long routeId) {
        StationInfo stationInfo = queryFactory.select(Projections.constructor(
                StationInfo.class,
                station.name,
                city.name))
            .from(route)
            .join(station)
            .on(route.station.eq(station))
            .join(city)
            .on(station.city.eq(city))
            .where(route.id.eq(routeId))
            .fetchOne();
        return Optional.ofNullable(stationInfo);
    }

    @Override
    public Optional<DriverInfo> findDriverInfo(Long routeId) {
        DriverInfo driverInfo = queryFactory.select(Projections.constructor(
                DriverInfo.class,
                driver.id,
                driver.name,
                driver.company,
                driver.phoneNumber,
                route.carNumber))
            .from(route)
            .join(driver)
            .on(route.driver.eq(driver))
            .where(route.id.eq(routeId))
            .fetchOne();
        return Optional.ofNullable(driverInfo);
    }

    @Override
    public Optional<BusInfo> findBusInfo(Long routeId) {
        BusInfo busInfo = queryFactory.select(Projections.constructor(
                BusInfo.class,
                bus.numberOfSeats))
            .from(route)
            .join(bus)
            .on(route.bus.eq(bus))
            .where(route.id.eq(routeId))
            .fetchOne();
        return Optional.ofNullable(busInfo);
    }

    @Override
    public Optional<SeatInfo> findSeatInfo(Long routeId, Long userId) {
        SeatInfo seatInfo = queryFactory.select(Projections.constructor(
                SeatInfo.class,
                seat.no,
                getReservationSeats(routeId)))
            .from(seat)
            .where(seat.user.id.eq(userId).and(seat.route.id.eq(routeId))).fetchOne();
        return Optional.ofNullable(seatInfo);
    }

    private JPQLQuery<Long> getSeatId(Long routeId, Long userId) {
        return JPAExpressions.select(seat.id).from(seat)
            .where(seat.user.id.eq(userId).and(seat.route.id.eq(routeId)));
    }

    private JPQLQuery<Integer> getTotalSeats(Long routeId) {
        return JPAExpressions.select(bus.numberOfSeats)
            .from(route)
            .where(route.id.eq(routeId))
            .join(bus)
            .on(route.bus.eq(bus));
    }

    private JPQLQuery<Integer> getReservationSeats() {
        return JPAExpressions.select(seat.id.count().intValue())
            .from(seat)
            .where(seat.route.id.eq(route.id)
                .and(seat.status.eq(SeatStatus.RESERVATION)));
    }

    private JPQLQuery<Integer> getReservationSeats(Long routeId) {
        return JPAExpressions.select(seat.id.count().intValue())
            .from(seat)
            .where(seat.route.id.eq(routeId)
                .and(seat.status.eq(SeatStatus.RESERVATION)));
    }

    public JPQLQuery<Long> getRouteId(Long userId) {
        return JPAExpressions.select(seat.route.id)
            .from(user)
            .join(seat)
            .on(seat.user.eq(user))
            .where(user.id.eq(userId));
    }
}

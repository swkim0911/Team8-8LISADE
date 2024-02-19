package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.request.SignUpRequest;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.BusInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.DriverInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.RouteAndFestivalInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.SeatInfo;
import com.lisade.togeduck.dto.response.UserReservedRouteDetailResponse.StationInfo;
import com.lisade.togeduck.dto.response.ValidateUserIdResponse;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.enums.AuthorityType;
import java.time.LocalTime;

public class UserMapper {

    public static User toUser(SignUpRequest signUpRequest) {
        return User.builder()
            .userId(signUpRequest.getUserId())
            .password(signUpRequest.getPassword())
            .nickname(signUpRequest.getNickname())
            .email(signUpRequest.getEmail())
            .authority(AuthorityType.ROLE_USER)
            .build();
    }

    public static ValidateUserIdResponse toValidateUserIdResponse(String userIdMessage) {
        return ValidateUserIdResponse.builder()
            .userId(userIdMessage)
            .build();
    }

    public static UserReservedRouteDetailResponse toUserReservedRouteDetailResponse(
        RouteAndFestivalInfo routeAndFestivalInfo, StationInfo stationInfo, SeatInfo seatInfo,
        BusInfo busInfo, DriverInfo driverInfo, LocalTime arrivedAt) {
        return UserReservedRouteDetailResponse.builder()
            .festivalId(routeAndFestivalInfo.getFestivalId())
            .title(routeAndFestivalInfo.getTitle())
            .seatNo(seatInfo.getSeatNo())
            .expectedDuration(routeAndFestivalInfo.getExpectedDuration())
            .status(routeAndFestivalInfo.getStatus())
            .totalSeats(busInfo.getTotalSeats())
            .reservedSeats(seatInfo.getReservedSeats())
            .price(routeAndFestivalInfo.getPrice())
            .imagePath(routeAndFestivalInfo.getImagePath())
            .startedAt(routeAndFestivalInfo.getStartedAt())
            .arrivedAt(arrivedAt)
            .stationName(stationInfo.getName())
            .stationCity(stationInfo.getCity())
            .festivalLocation(routeAndFestivalInfo.getFestivalLocation())
            .festivalCity(routeAndFestivalInfo.getFestivalCity())
            .driverInfo(driverInfo)
            .build();
    }
}

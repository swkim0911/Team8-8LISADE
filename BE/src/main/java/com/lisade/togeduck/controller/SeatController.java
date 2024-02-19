package com.lisade.togeduck.controller;

import com.lisade.togeduck.annotation.Login;
import com.lisade.togeduck.dto.request.SeatRegistrationRequest;
import com.lisade.togeduck.dto.response.SeatListResponse;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routes/{route_id}/seats")
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public SeatListResponse getList(
        @PathVariable(name = "route_id") Long routeId) {
        return seatService.getList(routeId);
    }

    @PostMapping
    public Long register(
        @Login User user,
        @PathVariable(name = "route_id") Long routeId,
        @RequestBody SeatRegistrationRequest seatRegistration) {
        return seatService.register(user, routeId, seatRegistration);
    }
}

package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.request.SeatRegistrationDto;
import com.lisade.togeduck.dto.response.SeatListDto;
import com.lisade.togeduck.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/festivals/{festival_id}/routes/{route_id}")
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/seats")
    public SeatListDto getList(
        @PathVariable(name = "festival_id") Long festivalId,
        @PathVariable(name = "route_id") Long routeId) {
        return seatService.getList(routeId);
    }

    @PatchMapping("/seats")
    public void register(
        @PathVariable(name = "festival_id") Long festivalId,
        @PathVariable(name = "route_id") Long routeId,
        @RequestBody SeatRegistrationDto seatRegistration) {
        seatService.register(routeId, seatRegistration);
    }
}

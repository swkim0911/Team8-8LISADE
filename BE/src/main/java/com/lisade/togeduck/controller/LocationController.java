package com.lisade.togeduck.controller;

import com.lisade.togeduck.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<Object> getList() {
        return ResponseEntity.ok(locationService.getList());
    }
}

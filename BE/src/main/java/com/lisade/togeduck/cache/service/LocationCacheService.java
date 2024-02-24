package com.lisade.togeduck.cache.service;

import com.lisade.togeduck.cache.repository.LocationCacheRepository;
import com.lisade.togeduck.cache.value.LocationCacheValue;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationCacheService {

    private final LocationCacheRepository locationCacheRepository;

    public Optional<LocationCacheValue> get(String locationId) {
        return locationCacheRepository.findByLocationId(locationId);
    }

    public void save(LocationCacheValue locationCacheValue) {
        locationCacheRepository.save(locationCacheValue);
    }
}

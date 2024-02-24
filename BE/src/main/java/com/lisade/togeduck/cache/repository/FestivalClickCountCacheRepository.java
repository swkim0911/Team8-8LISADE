package com.lisade.togeduck.cache.repository;

import com.lisade.togeduck.cache.value.FestivalClickCountCacheValue;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface FestivalClickCountCacheRepository extends
    CrudRepository<FestivalClickCountCacheValue, String> {

    Optional<FestivalClickCountCacheValue> findByFestivalId(Long festivalId);
}

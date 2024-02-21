package com.lisade.togeduck.cache;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface FestivalClickCountCacheRepository extends
    CrudRepository<FestivalClickCountCacheValue, String> {

    Optional<FestivalClickCountCacheValue> findByFestivalId(Long festivalId);
}

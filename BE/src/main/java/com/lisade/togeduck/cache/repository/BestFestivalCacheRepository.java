package com.lisade.togeduck.cache.repository;

import com.lisade.togeduck.cache.value.BestFestivalCacheValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestFestivalCacheRepository extends
    CrudRepository<BestFestivalCacheValue, String> {

}

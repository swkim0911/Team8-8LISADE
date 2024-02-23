package com.lisade.togeduck.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BestFestivalCacheRepository extends
    CrudRepository<BestFestivalCacheValue, String> {

}

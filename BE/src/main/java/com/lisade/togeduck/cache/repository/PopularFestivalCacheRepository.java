package com.lisade.togeduck.cache.repository;

import com.lisade.togeduck.cache.value.PopularFestivalCacheValue;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopularFestivalCacheRepository extends
    CrudRepository<PopularFestivalCacheValue, String> {

    Optional<PopularFestivalCacheValue> findByCategoryId(String categoryId);
}

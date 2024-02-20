package com.lisade.togeduck.cache;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationCacheRepository extends CrudRepository<LocationCacheValue, String> {

    Optional<LocationCacheValue> findByLocationId(String locationId);
}

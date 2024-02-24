package com.lisade.togeduck.cache.repository;

import com.lisade.togeduck.cache.value.UserClickLogCacheValue;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserClickLogCacheRepository extends
    CrudRepository<UserClickLogCacheValue, String> {

    Optional<UserClickLogCacheValue> findByUserId(String userId);
}

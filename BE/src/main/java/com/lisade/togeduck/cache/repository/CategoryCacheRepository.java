package com.lisade.togeduck.cache.repository;

import com.lisade.togeduck.cache.value.CategoryCacheValue;
import org.springframework.data.repository.CrudRepository;

public interface CategoryCacheRepository extends CrudRepository<CategoryCacheValue, String> {

}

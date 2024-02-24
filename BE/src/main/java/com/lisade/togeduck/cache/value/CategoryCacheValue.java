package com.lisade.togeduck.cache.value;

import com.lisade.togeduck.entity.Category;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryCacheValue {

    public static final String CATEGORY_CACHE_ID = "category";

    @Id
    private String id;
    private List<Category> categories;

    public CategoryCacheValue(List<Category> categories) {
        this.id = CATEGORY_CACHE_ID;
        this.categories = categories;
    }
}

package com.lisade.togeduck.cache.value;

import com.lisade.togeduck.dto.response.FestivalResponse;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("category_popular_festival")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularFestivalCacheValue {

    @Id
    private String categoryId;

    private List<FestivalResponse> festivalResponses;

    public PopularFestivalCacheValue(String categoryId, List<FestivalResponse> festivalResponses) {
        this.categoryId = categoryId;
        this.festivalResponses = festivalResponses;
    }
}

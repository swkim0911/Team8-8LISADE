package com.lisade.togeduck.cache.value;

import com.lisade.togeduck.dto.response.BestFestivalResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash("best_festival")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BestFestivalCacheValue {

    public static final String BEST_CACHE_ID = "best";
    @Id
    private String id;

    private BestFestivalResponse bests;

    public BestFestivalCacheValue(BestFestivalResponse bests) {
        this.id = BEST_CACHE_ID;
        this.bests = bests;
    }
}

package com.lisade.togeduck.cache.value;

import com.lisade.togeduck.dto.response.BestFestivalResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash("best_festival")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BestFestivalCacheValue {

    @Id
    private String id;

    private BestFestivalResponse bests;

    public BestFestivalCacheValue(String id, BestFestivalResponse bests) {
        this.id = id;
        this.bests = bests;
    }
}

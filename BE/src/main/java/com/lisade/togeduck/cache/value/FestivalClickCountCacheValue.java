package com.lisade.togeduck.cache.value;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Setter
@RedisHash("festival_click_count")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalClickCountCacheValue {

    @Id
    private String id;
    @Indexed
    private Long festivalId;
    private Integer clickCount;

    private FestivalClickCountCacheValue(Long festivalId, Integer clickCount) {
        this.festivalId = festivalId;
        this.clickCount = clickCount;
    }

    public static FestivalClickCountCacheValue of(Long festivalId, Integer clickCount) {
        return new FestivalClickCountCacheValue(festivalId, clickCount);
    }

    public void increaseClickCount() {
        clickCount++;
    }
}

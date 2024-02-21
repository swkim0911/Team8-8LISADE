package com.lisade.togeduck.cache;

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
    private Long clickCount;

    private FestivalClickCountCacheValue(Long festivalId, Long clickCount) {
        this.festivalId = festivalId;
        this.clickCount = clickCount;
    }

    public static FestivalClickCountCacheValue of(Long festivalId, Long clickCount) {
        return new FestivalClickCountCacheValue(festivalId, clickCount);
    }

    public void increaseClickCount() {
        clickCount++;
    }
}

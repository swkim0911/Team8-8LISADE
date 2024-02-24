package com.lisade.togeduck.cache.value;


import com.lisade.togeduck.dto.response.DistancePricesResponse;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash("location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationCacheValue implements Serializable {

    @Id
    private String id;
    @Indexed
    private String locationId;
    private DistancePricesResponse distancePricesResponse;
    @TimeToLive
    private Long ttl = 60 * 60 * 12L;

    private LocationCacheValue(String locationId, DistancePricesResponse distancePricesResponse) {
        this.locationId = locationId;
        this.distancePricesResponse = distancePricesResponse;
    }

    public static LocationCacheValue of(String locationId,
        DistancePricesResponse distancePricesResponse) {
        return new LocationCacheValue(locationId, distancePricesResponse);
    }
}

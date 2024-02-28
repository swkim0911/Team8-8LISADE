package com.lisade.togeduck.batch;

import com.lisade.togeduck.repository.FestivalRepository;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

@RequiredArgsConstructor
public class FestivalIdRangePartitioner implements Partitioner {

    private final FestivalRepository festivalRepository;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        Long maxId = festivalRepository.findMaxId();
        Long minId = festivalRepository.findMinId();

        long targetSize = (maxId - minId) / gridSize + 1;
        Map<String, ExecutionContext> result = new HashMap<>(gridSize);
        long num = 0L;
        long start = minId;
        long end = start + targetSize - 1;
        while (start <= maxId) {
            ExecutionContext ctx = new ExecutionContext();
            result.put("partition" + num, ctx);
            if (end >= maxId) {
                end = maxId;
            }
            ctx.putLong("minId", start);
            ctx.putLong("maxId", end);

            start += targetSize;
            end += targetSize;
            num++;
        }
        return result;
    }
}

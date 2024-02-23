package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.BestFestivalDto;
import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.BestFestivalResponse.Banner;
import com.lisade.togeduck.dto.response.FestivalDetailResponse;
import com.lisade.togeduck.dto.response.FestivalResponse;
import com.lisade.togeduck.dto.response.FestivalViewDto;
import com.lisade.togeduck.entity.Festival;
import com.lisade.togeduck.entity.FestivalImage;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = "spring")
public interface FestivalMapper {

    FestivalMapper INSTANCE = Mappers.getMapper(FestivalMapper.class);

    FestivalResponse toFestivalResponse(Festival festival);

    FestivalResponse toFestivalResponse(FestivalViewDto festivalViewDto);

    @Mapping(target = "paths", source = "festivalImages")
    @Mapping(target = "category", source = "category.name")
    FestivalDetailResponse toFestivalDetailResponse(Festival festival);

    @Mapping(target = "totalSize", source = "totalSize")
    @Mapping(target = "banner", source = "bestFestivalDto", qualifiedByName = "toBanner")
    BestFestivalResponse toBestFestivalResponse(Integer totalSize,
        List<BestFestivalDto> bestFestivalDto);

    @Named("toBanner")
    default List<Banner> dtoToBanner(List<BestFestivalDto> bestFestivalDto) {
        return bestFestivalDto.stream()
            .map(dto -> Banner.builder().id(dto.getId()).thumbnailPath(dto.getThumbnailPath())
                .build())
            .toList();
    }

    default List<String> festivalImagesToPaths(List<FestivalImage> festivalImages) {
        return festivalImages.stream()
            .map(FestivalImage::getPath)
            .toList();
    }

    default Slice<FestivalResponse> toFestivalResponseSlice(Slice<Festival> festivals) {
        return festivals.map(this::toFestivalResponse);
    }
}

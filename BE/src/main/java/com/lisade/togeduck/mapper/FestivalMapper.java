package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.response.BestFestivalDto;
import com.lisade.togeduck.dto.response.BestFestivalResponse;
import com.lisade.togeduck.dto.response.BestFestivalResponse.Banner;
import com.lisade.togeduck.dto.response.FestivalDetailDto;
import com.lisade.togeduck.dto.response.FestivalDto;
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

    @Mapping(target = "paths", source = "festivalImages")
    FestivalDto toFestivalDto(Festival festival);

    @Mapping(target = "paths", source = "festivalImages")
    @Mapping(target = "category", source = "category.name")
    FestivalDetailDto toFestivalDetailDto(Festival festival);

    @Mapping(target = "totalSize", source = "totalSize")
    @Mapping(target = "banner", source = "bestFestivalDto", qualifiedByName = "toBanner")
    BestFestivalResponse toBestFestivalResponse(Integer totalSize,
        List<BestFestivalDto> bestFestivalDto);

    @Named("toBanner")
    default List<Banner> dtoToBanner(List<BestFestivalDto> bestFestivalDto) {
        return bestFestivalDto.stream()
            .map(dto -> Banner.builder().id(dto.getId()).path(dto.getPath()).build())
            .toList();
    }

    default List<String> festivalImagesToPaths(List<FestivalImage> festivalImages) {
        return festivalImages.stream()
            .map(FestivalImage::getPath)
            .toList();
    }

    default Slice<FestivalDto> toFestivalDtoSlice(Slice<Festival> festivals) {
        return festivals.map(this::toFestivalDto);
    }
}

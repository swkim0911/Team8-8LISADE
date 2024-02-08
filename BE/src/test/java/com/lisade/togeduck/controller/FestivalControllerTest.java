package com.lisade.togeduck.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lisade.togeduck.dto.FestivalDto;
import com.lisade.togeduck.entity.Category;
import com.lisade.togeduck.entity.Status;
import com.lisade.togeduck.service.FestivalService;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(FestivalController.class)
@MockBean(JpaMetamodelMappingContext.class)
class FestivalControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    FestivalService festivalService;
    @InjectMocks
    private FestivalController festivalController;

    @Test
    @DisplayName("사용자가 페스티벌 리스트를 요청하였을때, page와 size, category를 입력받고 Slice형태로 Json을 return한다.")
    void getList() throws Exception {

        //given
        FestivalDto mockFestivalDto = FestivalDto.builder().id(1L)
            .title("test1")
            .location("테스트 로케이션")
            .paths(List.of("filePath"))
            .startedAt(LocalDate.of(2024, 2, 8)).build();

        Slice<FestivalDto> mockSlice = new PageImpl<>(Collections.singletonList(mockFestivalDto));

        //when
        when(festivalService.getList(any(Pageable.class), any(Category.class),
            any(Status.class), any(String.class))).thenReturn(mockSlice);

        ResultActions resultActions = mockMvc.perform(get("/festivals")
                .param("category", "3")
                .param("filter", "popular")
                .param("status", "1")
                .param("page", "1")
                .param("size", "5")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        //then
        verify(festivalService).getList(
            eq(PageRequest.of(1, 5, Sort.by(Sort.Direction.ASC, "startedAt"))),
            eq(Category.CONCERT),
            eq(Status.RECRUITMENT), eq("popular"));

        resultActions.andExpect(jsonPath("$.content[0].id").value(mockFestivalDto.getId()))
            .andExpect(jsonPath("$.content[0].title").value(mockFestivalDto.getTitle()))
            .andExpect(jsonPath("$.content[0].location").value(mockFestivalDto.getLocation()))
            .andExpect(jsonPath("$.content[0].paths").value(mockFestivalDto.getPaths().get(0)))
            .andExpect(jsonPath("$.content[0].startedAt").value("2024-02-08"));
    }

    @Test
    @DisplayName("사용자가 존재하지 않는 카테고리를 선택할 시 IllegalArgumentExcaption을 던진다.")
    void getIllegalCategoryThenThrowException() throws Exception {

        //given
        Pageable pageable = mock(Pageable.class);
        String invalidCategoryCode = "InvalidCode";

        //when & then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class, () -> {
                festivalController.getList(pageable, Category.of(invalidCategoryCode), null, null);
            });
        Assertions.assertThat(exception.getMessage()).isEqualTo("일치하는 Category 코드가 없습니다.");
        verify(festivalService, never()).getList(any(), any(), any(), any());
    }
}

package com.example.project.service;

import com.example.project.constants.Constants;
import com.example.project.dto.StudioDetailsDto;
import com.example.project.dto.StudioDto;
import com.example.project.entity.Studio;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.mapper.StudioMapper;
import com.example.project.repository.StudioRepository;
import com.example.project.service.implementations.StudioServiceImpl;
import com.example.project.utils.StudioMocks;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudioServiceImplTest {
    @InjectMocks
    private StudioServiceImpl studioService;

    @Mock
    private com.example.project.repository.StudioRepository studioRepository;

    @Mock
    private com.example.project.mapper.StudioMapper studioMapper;

    @Test
    public void getStudiosTest() {
        StudioDetailsDto studioDetailsDto = StudioMocks.mockStudioDetailsDto();
        List<StudioDetailsDto> studioDetailsDtoList = Arrays.asList(studioDetailsDto);
        Studio studio = StudioMocks.mockStudio();
        List<Studio> studios = Arrays.asList(studio);

        when(studioRepository.findAll()).thenReturn(studios);
        when(studioMapper.mapToStudioDetailsDto(studio)).thenReturn(studioDetailsDto);

        List<StudioDetailsDto> result = studioService.getStudios();
        assertEquals(result, studioDetailsDtoList);
    }

    @Test
    public void addStudioTest() {
        Studio studio = StudioMocks.mockStudio();
        StudioDto studioDto = StudioMocks.mockStudioDto();

        when(studioRepository.save(studio)).thenReturn(studio);
        when(studioMapper.mapToStudio(studioDto)).thenReturn(studio);
        when(studioMapper.mapToStudioDto(studio)).thenReturn(studioDto);
        StudioDto result = studioService.add(studioDto);

        assertEquals(result.getName(), studioDto.getName());
        assertEquals(result.getCapacity(), studioDto.getCapacity());
    }

    @Test
    public void addStudioThrowsUniqueConstraintExceptionTest() {
        StudioDto studioDto = StudioMocks.mockStudioDto();
        Studio studio = StudioMocks.mockStudio();
        when(studioRepository.findByName("Test Studio")).thenReturn(Optional.of(studio));

        NotUniqueException uniqueConstraintException = assertThrows(NotUniqueException.class, () -> studioService.add(studioDto));
        assertEquals(String.format(Constants.NOT_UNIQUE_ERROR, "studio", "studio"), uniqueConstraintException.getMessage());
    }

    @Test
    public void editStudioTest() {
        Studio studio = StudioMocks.mockStudio();
        StudioDto studioDto = StudioMocks.mockStudioDto();
        studio.setName("Test Studio edit");

        when(studioRepository.findById(1L)).thenReturn(Optional.of(studio));
        studioDto.setName("Test Studio edit");
        when(studioMapper.mapToStudioDto(studio)).thenReturn(studioDto);
        when(studioRepository.save(studio)).thenReturn(studio);
        StudioDto result = studioService.editStudio(1L, studioDto);

        assertEquals(result.getName(), studioDto.getName());
        assertEquals(result.getName(), "Test Studio edit");
    }

    @Test
    public void editStudioThrowsEntityNotFoundExceptionTest() {
        Studio studio = StudioMocks.mockStudio();
        StudioDto studioDto = StudioMocks.mockStudioDto();
        studio.setName("Test Studio edit");

        when(studioRepository.findById(1L)).thenReturn(Optional.empty());
        NoEntityFoundException entityNotFoundException = assertThrows(NoEntityFoundException.class, () -> studioService.editStudio(1L, studioDto));
        assertEquals(String.format(Constants.NO_ENTITY_FOUND, "studio", "id", 1), entityNotFoundException.getMessage());
    }

    @Test
    public void editStudioThrowsUniqueConstraintExceptionTest() {
        Studio studio = StudioMocks.mockStudio();
        StudioDto studioDto = StudioMocks.mockStudioDto();
        studioDto.setName("Test Studio edit");
        studio.setName("Test Studio edit");
        Studio testStudio = StudioMocks.mockStudio();
        testStudio.setStudio_id(2L);
        testStudio.setName("Test Studio edit");

        when(studioRepository.findById(1L)).thenReturn(Optional.of(studio));
        when(studioRepository.findByName("Test Studio edit")).thenReturn(Optional.of(testStudio));
        NotUniqueException uniqueConstraintException = assertThrows(NotUniqueException.class, () -> studioService.editStudio(1L, studioDto));
        assertEquals(String.format(Constants.NOT_UNIQUE_ERROR, "studio", "studio"), uniqueConstraintException.getMessage());
    }
}

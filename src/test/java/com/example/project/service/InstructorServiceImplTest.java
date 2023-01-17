package com.example.project.service;

import com.example.project.constants.Constants;
import com.example.project.dto.InstructorDetailsDto;
import com.example.project.dto.InstructorDto;
import com.example.project.entity.Instructor;
import com.example.project.entity.Class;
import com.example.project.exceptions.BadRequestException;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.mapper.InstructorMapper;
import com.example.project.repository.InstructorRepository;
import com.example.project.service.implementations.InstructorServiceImpl;
import com.example.project.utils.ClassMocks;
import com.example.project.utils.InstructorMocks;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InstructorServiceImplTest {
        @InjectMocks
        private InstructorServiceImpl instructorService;

        @Mock
        private InstructorRepository instructorRepository;

        @Mock
        private InstructorMapper instructorMapper;

        @Test
        public void getInstructorsTest() {
            InstructorDetailsDto instructorDetailsDto = InstructorMocks.mockInstructorDetailsDto();
            List<InstructorDetailsDto> instructorDetailsDtoList = Arrays.asList(instructorDetailsDto);
            Instructor instructor = InstructorMocks.mockInstructor();
            List<Instructor> instructors = Arrays.asList(instructor);

            when(instructorRepository.findAll()).thenReturn(instructors);
            when(instructorMapper.mapToInstructorDetailsDto(instructor)).thenReturn(instructorDetailsDto);

            List<InstructorDetailsDto> result = instructorService.getInstructors();
            assertEquals(result, instructorDetailsDtoList);
        }

        @Test
        public void addInstructorTest() {
            Instructor instructor = InstructorMocks.mockInstructor();
            InstructorDto instructorDto = InstructorMocks.mockInstructorDto();

            when(instructorRepository.save(instructor)).thenReturn(instructor);
            when(instructorMapper.mapToInstructor(instructorDto)).thenReturn(instructor);
            when(instructorMapper.mapToInstructorDto(instructor)).thenReturn(instructorDto);
            InstructorDto result = instructorService.add(instructorDto);

            assertEquals(result.getName(), instructorDto.getName());
        }

        @Test
        public void addInstructorThrowsUniqueConstraintExceptionTest() {
            Instructor instructor = InstructorMocks.mockInstructor();
            InstructorDto instructorDto = InstructorMocks.mockInstructorDto();
            when(instructorRepository.findByName("Test instructor")).thenReturn(Optional.of(instructor));

            NotUniqueException uniqueConstraintException = assertThrows(NotUniqueException.class, () -> instructorService.add(instructorDto));
            assertEquals(String.format(Constants.NOT_UNIQUE_ERROR, "Instructor", "name"), uniqueConstraintException.getMessage());
        }

        @Test
        public void editInstructorTest() {
            Instructor instructor = InstructorMocks.mockInstructor();
            InstructorDto instructorDto = InstructorMocks.mockInstructorDto();
            instructor.setName("Test instructor edit");

            when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
            instructorDto.setName("Test instructor edit");
            when(instructorMapper.mapToInstructorDto(instructor)).thenReturn(instructorDto);
            when(instructorRepository.save(instructor)).thenReturn(instructor);
            InstructorDto result = instructorService.editInstructor(1L, instructorDto);

            assertEquals(result.getName(), instructorDto.getName());
            assertEquals(result.getName(), "Test instructor edit");
        }

        @Test
        public void editInstructorThrowsEntityNotFoundExceptionTest() {
            Instructor instructor = InstructorMocks.mockInstructor();
            InstructorDto instructorDto = InstructorMocks.mockInstructorDto();
            instructor.setName("Test instructor edit");

            when(instructorRepository.findById(1L)).thenReturn(Optional.empty());
            NoEntityFoundException entityNotFoundException = assertThrows(NoEntityFoundException.class, () -> instructorService.editInstructor(1L, instructorDto));
            assertEquals(String.format(Constants.NO_ENTITY_FOUND, "Instructor", "id", 1), entityNotFoundException.getMessage());
        }

        @Test
        public void editInstructorThrowsUniqueConstraintExceptionTest() {
            Instructor instructor = InstructorMocks.mockInstructor();
            InstructorDto instructorDto = InstructorMocks.mockInstructorDto();
            instructor.setName("Test instructor edit");
            instructorDto.setName("Test instructor edit");
            Instructor testInstructor = InstructorMocks.mockInstructor();
            testInstructor.setInstructor_id(2L);
            testInstructor.setName("Test instructor edit");

            when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
            when(instructorRepository.findByName("Test instructor edit")).thenReturn(Optional.of(testInstructor));
            NotUniqueException uniqueConstraintException = assertThrows(NotUniqueException.class, () -> instructorService.editInstructor(1L, instructorDto));
            assertEquals(String.format(Constants.NOT_UNIQUE_ERROR, "Instructor", "name"), uniqueConstraintException.getMessage());
        }

        @Test
        public void deleteInstructorTest() {
            Instructor instructor = InstructorMocks.mockInstructor();
            InstructorDto instructorDto = InstructorMocks.mockInstructorDto();

            when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
            when(instructorMapper.mapToInstructorDto(instructor)).thenReturn(instructorDto);
            InstructorDto result = instructorService.deleteInstructor(1L);

            assertEquals(result.getName(), instructorDto.getName());
            assertEquals(result.getName(), "Test instructor");
        }

        @Test
        public void deleteInstructorThrowsEntityNotFoundExceptionTest() {
            when(instructorRepository.findById(1L)).thenReturn(Optional.empty());
            NoEntityFoundException entityNotFoundException = assertThrows(NoEntityFoundException.class, () -> instructorService.deleteInstructor(1L));
            assertEquals(String.format(Constants.NO_ENTITY_FOUND, "Instructor", "id", 1), entityNotFoundException.getMessage());
        }

        @Test
        public void deleteInstructorThrowsBadRequestExceptionTest() {
            Instructor instructor = InstructorMocks.mockInstructor();
            Class cls = ClassMocks.mockClass();
            instructor.getClasses().add(cls);

            when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
            BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> instructorService.deleteInstructor(1L));
            assertEquals(Constants.INSTRUCTOR_DELETION_ERROR, badRequestException.getMessage());
        }

}

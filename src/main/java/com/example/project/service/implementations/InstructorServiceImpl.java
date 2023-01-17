package com.example.project.service.implementations;

import com.example.project.constants.Constants;
import com.example.project.dto.InstructorDetailsDto;
import com.example.project.dto.InstructorDto;
import com.example.project.entity.Instructor;
import com.example.project.exceptions.BadRequestException;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.mapper.InstructorMapper;
import com.example.project.repository.InstructorRepository;
import com.example.project.service.CommonService;
import com.example.project.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorServiceImpl implements InstructorService, CommonService<InstructorDto, InstructorDto> {

    private final InstructorRepository instructorRepository;

    private final InstructorMapper instructorMapper;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository, InstructorMapper instructorMapper) {
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
    }

    @Override
    public List<InstructorDetailsDto> getInstructors() {
        return instructorRepository.findAll().stream().map(instructorMapper::mapToInstructorDetailsDto).collect(Collectors.toList());
    }

    @Override
    public InstructorDto add(InstructorDto InstructorDto) {
        Optional<Instructor> optionalInstructor = instructorRepository.findByName(InstructorDto.getName());
        if(optionalInstructor.isPresent()) {
            throw new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "name"));
        }
        return instructorMapper.mapToInstructorDto(instructorRepository.save(instructorMapper.mapToInstructor(InstructorDto)));
    }

    @Override
    public InstructorDto editInstructor(Long id, InstructorDto InstructorDto) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if(!optionalInstructor.isPresent()) {
            throw new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "Instructor", "id", id));
        }

        Optional<Instructor> optionalInstructorByName = instructorRepository.findByName(InstructorDto.getName());
        if(optionalInstructorByName.isPresent() && !Objects.equals(optionalInstructorByName.get().getInstructor_id(), id)) {
            throw new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "name"));
        }

        Instructor Instructor = optionalInstructor.get();
        Instructor.setName(InstructorDto.getName());

        return instructorMapper.mapToInstructorDto(instructorRepository.save(Instructor));
    }

    @Override
    public InstructorDto deleteInstructor(Long id) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if(!optionalInstructor.isPresent()) {
            throw new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "Instructor", "id", id));
        }

        Instructor Instructor = optionalInstructor.get();

        if(!Instructor.getClasses().isEmpty()) {
            throw new BadRequestException(Constants.INSTRUCTOR_DELETION_ERROR);
        }

        InstructorDto instructorDto = instructorMapper.mapToInstructorDto(Instructor);
        instructorRepository.deleteById(id);

        return instructorDto;
    }
}
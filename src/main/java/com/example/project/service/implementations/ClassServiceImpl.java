package com.example.project.service.implementations;

import com.example.project.constants.Constants;
import com.example.project.dto.ClassDetailsDto;
import com.example.project.dto.ClassDto;
import com.example.project.dto.ClassScheduleDto;
import com.example.project.dto.NewClassDto;
import com.example.project.entity.ClassSchedule;
import com.example.project.entity.Instructor;
import com.example.project.entity.Studio;
import com.example.project.entity.Class;
import com.example.project.enums.EDayOfWeek;
import com.example.project.enums.ELevel;
import com.example.project.exceptions.BadRequestException;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.exceptions.NotUniqueException;
import com.example.project.helper.ClassScheduleId;
import com.example.project.mapper.ClassMapper;
import com.example.project.mapper.ClassScheduleMapper;
import com.example.project.repository.ClassRepository;
import com.example.project.repository.ClassScheduleRepository;
import com.example.project.repository.InstructorRepository;
import com.example.project.repository.StudioRepository;
import com.example.project.service.ClassService;
import com.example.project.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService, CommonService<ClassDetailsDto, NewClassDto> {

    private final ClassRepository classRepository;

    private final InstructorRepository instructorRepository;

    private final StudioRepository studioRepository;

    private final ClassScheduleRepository classScheduleRepository;

    private final ClassMapper classMapper;

    private final ClassScheduleMapper classScheduleMapper;

    @Autowired
    public ClassServiceImpl(ClassRepository classRepository, InstructorRepository instructorRepository, StudioRepository studioRepository, ClassScheduleRepository classScheduleRepository, ClassMapper classMapper, ClassScheduleMapper classScheduleMapper) {
        this.classRepository = classRepository;
        this.instructorRepository = instructorRepository;
        this.studioRepository = studioRepository;
        this.classScheduleRepository = classScheduleRepository;
        this.classMapper = classMapper;
        this.classScheduleMapper = classScheduleMapper;
    }

    @Override
    public ClassDetailsDto add(NewClassDto newClassDto) {
        Optional<Class> optionalClass = classRepository.findByName(newClassDto.getName());
        if(optionalClass.isPresent()) {
            throw new NotUniqueException(String.format(Constants.NOT_UNIQUE_ERROR, "name"));
        }

        List<Instructor> instructors = new ArrayList<>();
        for(Long InstructorId : newClassDto.getInstructorsIds()) {
            Optional<Instructor> optionalInstructor = instructorRepository.findById(InstructorId);
            if(!optionalInstructor.isPresent()) {
                throw new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "Instructor", "id", InstructorId));
            }
            instructors.add(optionalInstructor.get());
        }
        ClassDto classDto = classMapper.mapToClassDto(newClassDto, instructors);
        Class cls = classRepository.save(classMapper.mapToClass(classDto));
        cls.setClassSchedules(new ArrayList<>());

        return classMapper.mapToClassDetailsDto(cls);
    }

    @Override
    public List<ClassDetailsDto> searchClasses(String name, EDayOfWeek dayOfWeek, ELevel level) {
        List<ClassDetailsDto> classes = classRepository.findAll().stream().map(classMapper::mapToClassDetailsDto).collect(Collectors.toList());

        if(name != null) {
            classes = classes.stream().filter(cls -> name.equals(cls.getName())).collect(Collectors.toList());
        }
        if(dayOfWeek != null) {
            for(ClassDetailsDto cls : classes) {
                List<ClassDetailsDto.ClassScheduleDetails> scheduleDetails =
                        new ArrayList<>(cls.getScheduleDetails().stream().filter(schedule -> dayOfWeek.equals(schedule.getDayOfWeek())).collect(Collectors.toList()));
                if(!scheduleDetails.isEmpty()) {
                    cls.setScheduleDetails(scheduleDetails);
                }
            }
        }
        if(level != null) {
            classes = classes.stream().filter(cls -> level.equals(cls.getLevel())).collect(Collectors.toList());
        }

        return classes;
    }

    @Override
    public ClassScheduleDto scheduleClass(ClassScheduleDto classScheduleDto) {
        String className = classScheduleDto.getClassName();
        Optional<Class> optionalClass = classRepository.findByName(className);
        if(!optionalClass.isPresent()) {
            throw new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "Class", "name", className));
        }

        String studioName = classScheduleDto.getStudioName();
        Optional<Studio> optionalStudio = studioRepository.findByName(studioName);
        if(!optionalStudio.isPresent()) {
            throw new NoEntityFoundException(String.format(Constants.NO_ENTITY_FOUND, "Studio", "name", studioName));
        }

        Class cls = optionalClass.get();
        EDayOfWeek dayOfWeek = classScheduleDto.getDayOfWeek();
        LocalTime startTime = classScheduleDto.getStartTime();
        LocalTime scheduleEndTime = startTime.plusMinutes(cls.getHoursDuration());

        if(startTime.isBefore(LocalTime.now())) {
            throw new BadRequestException(Constants.SCHEDULE_ERROR);
        }

        Studio studio = optionalStudio.get();
        for(ClassSchedule classSchedule : studio.getClassSchedules()) {
            LocalTime start = classSchedule.getId().getStartTime();
            LocalTime endTime = startTime.plusMinutes(classSchedule.getCls().getHoursDuration());
            if(startTime.isBefore(endTime) && start.isBefore(scheduleEndTime)) {
                throw new BadRequestException(Constants.SCHEDULE_ERROR);
            }
        }

        ClassScheduleId classScheduleId = ClassScheduleId.builder()
                .classId(cls.getClass_id())
                .studioId(studio.getStudio_id())
                .dayOfWeek(dayOfWeek)
                .startTime(startTime)
                .build();
        ClassSchedule classSchedule = ClassSchedule.builder()
                .id(classScheduleId)
                .classPrice(classScheduleDto.getPrice())
                .cls(cls)
                .studio(studio)
                .reservationTickets(new ArrayList<>())
                .build();
        return classScheduleMapper.mapToClassScheduleDto(classScheduleRepository.save(classSchedule));
    }
}
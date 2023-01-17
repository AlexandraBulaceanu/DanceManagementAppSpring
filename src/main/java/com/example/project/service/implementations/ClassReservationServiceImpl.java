package com.example.project.service.implementations;

import com.example.project.constants.Constants;
import com.example.project.dto.ClassReservationDetailsDto;
import com.example.project.dto.ClassReservationDto;
import com.example.project.entity.*;
import com.example.project.entity.Class;
import com.example.project.enums.EDayOfWeek;
import com.example.project.exceptions.BadRequestException;
import com.example.project.exceptions.NoEntityFoundException;
import com.example.project.helper.ClassScheduleId;
import com.example.project.mapper.ClassReservationMapper;
import com.example.project.repository.*;
import com.example.project.service.CommonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassReservationServiceImpl implements CommonService<ClassReservationDetailsDto, ClassReservationDto> {

    private final ClassReservationRepository classReservationRepository;

    private final StudentRepository studentRepository;

    private final ClassRepository classRepository;

    private final StudioRepository studioRepository;

    private final ClassScheduleRepository classScheduleRepository;

    private final ReservationTicketRepository reservationTicketRepository;

    private final ClassReservationMapper classReservationMapper;

    @Autowired
    public ClassReservationServiceImpl(ClassReservationRepository classReservationRepository, StudentRepository studentRepository, ClassRepository classRepository, StudioRepository studioRepository, ClassScheduleRepository classScheduleRepository, ReservationTicketRepository reservationTicketRepository, ClassReservationMapper classReservationMapper) {
        this.classReservationRepository = classReservationRepository;
        this.studentRepository = studentRepository;
        this.classRepository = classRepository;
        this.studioRepository = studioRepository;
        this.classScheduleRepository = classScheduleRepository;
        this.reservationTicketRepository = reservationTicketRepository;
        this.classReservationMapper = classReservationMapper;
    }

    @Override
    @Transactional
    public ClassReservationDetailsDto add(ClassReservationDto classReservationDto) {
        Optional<Student> optionalStudent = studentRepository.findByEmail(classReservationDto.getStudentEmail());
        if(!optionalStudent.isPresent()) {
            throw new NoEntityFoundException(Constants.NO_ENTITY_FOUND + " Student " + classReservationDto.getStudentEmail());
        }

        Optional<Class> optionalClass = classRepository.findByName(classReservationDto.getClassName());
        if(!optionalClass.isPresent()) {
            throw new NoEntityFoundException(Constants.NO_ENTITY_FOUND + " Class " + classReservationDto.getClassName());
        }

        Optional<Studio> optionalStudio = studioRepository.findByName(classReservationDto.getStudioName());
        if(!optionalStudio.isPresent()) {
            throw new NoEntityFoundException(Constants.NO_ENTITY_FOUND + "Studio" + " name " + classReservationDto.getStudioName());
        }

        Class cls = optionalClass.get();
        Studio studio = optionalStudio.get();
        ClassScheduleId classScheduleId = ClassScheduleId.builder()
                .classId(cls.getClass_id())
                .studioId(studio.getStudio_id())
                .dayOfWeek(classReservationDto.getDayOfWeek())
                .startTime(classReservationDto.getStartTime())
                .build();
        Optional<ClassSchedule> optionalClassSchedule = classScheduleRepository.findById(classScheduleId);
        if(!optionalClassSchedule.isPresent()) {
            throw new BadRequestException(Constants.NO_ENTITY_FOUND + "no class");
        }

        ClassSchedule classSchedule = optionalClassSchedule.get();
        int studioCapacity = studio.getCapacity();
        int soldReservationTickets = classSchedule.getReservationTickets().size();
        if(studioCapacity - soldReservationTickets < 0) {
            throw new BadRequestException(Constants.CLASS_FULLY_BOOKED);
        }

        Optional<ReservationTicket> optionalLastReservationTicket = reservationTicketRepository.findLastTicketBoughtForClass(classSchedule);

        LocalDate datePayment = LocalDate.now();
        if(optionalLastReservationTicket.isPresent()) {
            datePayment = optionalLastReservationTicket.get().getDatePaid();
        }

        List<ReservationTicket> tickets = new ArrayList<>();
        for(ReservationTicket t : tickets) {
            ReservationTicket ticket = ReservationTicket.builder()
                                            .datePaid(datePayment)
                                            .classSchedule(classSchedule)
                                            .build();
            tickets.add(ticket);
        }

        ClassReservation classReservation = ClassReservation.builder()
                .price(classSchedule.getClassPrice())
                .dayOfWeek(EDayOfWeek.TUESDAY)
                .startTime(LocalTime.now())
                .reservationTickets(tickets)
                .build();
        return classReservationMapper.mapToClassReservationDetailsDto(classReservationRepository.save(classReservation));
    }
}
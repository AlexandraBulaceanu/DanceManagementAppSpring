package com.example.project.repository;

import com.example.project.entity.ClassSchedule;
import com.example.project.entity.ReservationTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReservationTicketRepository extends JpaRepository<ReservationTicket, Long> {
    @Query("SELECT ticket " +
            "FROM ReservationTicket ticket " +
            "WHERE ticket.classSchedule = ?1 " +
            "ORDER BY ticket.datePaid DESC " +
            "LIMIT 1")
    Optional<ReservationTicket> findLastTicketBoughtForClass(ClassSchedule classSchedule);
}
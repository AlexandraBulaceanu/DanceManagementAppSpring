package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.example.project.entity.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    Optional<Class> findByName(String name);
}
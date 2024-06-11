package com.example.diplom2.repository;

import com.example.diplom2.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Long> {
    List<Stage> findAllByEventId(long event_id);
}
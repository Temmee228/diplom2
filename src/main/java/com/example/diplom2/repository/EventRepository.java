package com.example.diplom2.repository;

import com.example.diplom2.entity.Event;
import com.example.diplom2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(Long id);
int countEventsByParticipants(Set<User> participants);
}

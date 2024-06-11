package com.example.diplom2.service;

import com.example.diplom2.DTO.EventDTO;
import com.example.diplom2.DTO.StageDTO;
import com.example.diplom2.DTO.UserDTO;
import com.example.diplom2.entity.Stage;
import com.example.diplom2.mapper.EventMapper;
import com.example.diplom2.entity.Event;
import com.example.diplom2.entity.User;
import com.example.diplom2.repository.EventRepository;
import com.example.diplom2.repository.StageRepository;
import com.example.diplom2.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final StageRepository stageRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository, StageRepository stageRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.stageRepository = stageRepository;
    }

    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(EventMapper::toEventDTO)
                .collect(Collectors.toList());
    }

    public EventDTO getEventDTOById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Set<UserDTO> participants = event.getParticipants().stream()
                .map(EventMapper::toUserDTO)
                .collect(Collectors.toSet());

        Set<StageDTO> stages = event.getStages().stream()
                .map(EventMapper::toStageDTO)
                .collect(Collectors.toSet());

        return new EventDTO(event.getId(), event.getNameEvent(), event.getDescription(), event.getDate(), stages, participants);
    }

    public void addParticipant(Long eventId, String username) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        event.getParticipants().add(user);
        eventRepository.save(event);
    }


    public int countUserEvents(User user) {
        Set<User> users = new HashSet<>();
        users.add(user);
        return eventRepository.countEventsByParticipants(users);
    }
    public void addEvent(String nameEvent, String date) {
        Event event = new Event();
        event.setNameEvent(nameEvent);
        event.setDate(date);
        eventRepository.save(event);
    }
    public Event findById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }


    public Event save(Event event) {
        eventRepository.save(event);
        return event;
    }


    public void delete(Long id) {
        eventRepository.deleteById(id);
    }


    public void saveStage(Long eventId, Stage stage) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        stage.setEvent(event);
        stageRepository.save(stage);
    }


    public void deleteStage(Long eventId, Long stageId) {
        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new RuntimeException("Stage not found"));
        stageRepository.delete(stage);
    }
   /* public void addComment(Long eventId, User user, String comment) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        event.getComments().put(user, comment);
        eventRepository.save(event);
    }*/

    public void addStageComment(Long stageId, User user, String comment) {
        Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new RuntimeException("Stage not found"));
        stage.getComments().put(user, comment);
        stageRepository.save(stage);
    }
    /*public void addEventComment(Long eventId, User user, String commentText) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        event.getComments().put(user, commentText);
        eventRepository.save(event);
    }*/

    public void addComment(Long eventId, User user, String commentText) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + eventId));

        event.getComments().put(user, commentText);
        eventRepository.save(event);
    }


}
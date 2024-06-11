package com.example.diplom2.mapper;

import com.example.diplom2.DTO.EventDTO;
import com.example.diplom2.DTO.StageDTO;
import com.example.diplom2.DTO.UserDTO;
import com.example.diplom2.entity.Event;
import com.example.diplom2.entity.Stage;
import com.example.diplom2.entity.User;

import java.util.stream.Collectors;

public class EventMapper {
    public static EventDTO toEventDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getNameEvent(),
                event.getDescription(),
                event.getDate(),
                event.getStages().stream().map(EventMapper::toStageDTO).collect(Collectors.toSet()),
                event.getParticipants().stream().map(EventMapper::toUserDTO).collect(Collectors.toSet())
        );
    }

    public static StageDTO toStageDTO(Stage stage) {
        return new StageDTO(
                stage.getId(),
                stage.getName(),
                stage.getDate(),
                stage.getDescription()
        );
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getEmail()
        );
    }
}
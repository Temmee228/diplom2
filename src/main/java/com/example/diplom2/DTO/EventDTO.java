package com.example.diplom2.DTO;

import com.example.diplom2.entity.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class EventDTO {
    private long id;
    private String nameEvent;
    private String description;
    private String date;
    private Set<StageDTO> stages;
    private Set<UserDTO> participants;
    private Map<String, String> comments;

    public EventDTO() {
    }

    public EventDTO(long id, String nameEvent, String description, String date, Set<StageDTO> stages, Set<UserDTO> participants) {
        this.id = id;
        this.nameEvent = nameEvent;
        this.description = description;
        this.date = date;
        this.stages = stages;
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "id=" + id +
                ", nameEvent='" + nameEvent + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", stages=" + stages +
                ", participants=" + participants +
                '}';
    }
}
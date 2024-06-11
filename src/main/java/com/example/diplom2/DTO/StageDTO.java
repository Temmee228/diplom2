package com.example.diplom2.DTO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class StageDTO {
    private long id;
    private String name;
    private String date;
    private String description;

    public StageDTO() {
    }

    public StageDTO(long id, String name, String date, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.description = description;
    }
}
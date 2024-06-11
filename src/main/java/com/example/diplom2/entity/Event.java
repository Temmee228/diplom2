package com.example.diplom2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nameEvent;
    private String date;
    private String description;
    private String time;

    @ElementCollection
    @CollectionTable(name = "event_comments", joinColumns = @JoinColumn(name = "event_id"))
    @MapKeyJoinColumn(name = "user_id")
    @Column(name = "comment")
    private Map<User, String> comments = new HashMap<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Stage> stages = new HashSet<>();

    @ManyToMany
    private Set<User> participants = new HashSet<>();
}
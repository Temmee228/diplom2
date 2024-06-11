package com.example.diplom2.controller;

import com.example.diplom2.DTO.EventDTO;
import com.example.diplom2.entity.Event;
import com.example.diplom2.entity.Stage;
import com.example.diplom2.entity.User;
import com.example.diplom2.repository.StageRepository;
import com.example.diplom2.service.EventService;
import com.example.diplom2.service.UserService;
import com.example.diplom2.service.UserServiceImpl;
import com.google.gson.Gson;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class EventController {
    private final UserService userService;
    private final UserServiceImpl userServiceimp;
    private final EventService eventService;
    private final StageRepository stageRepository;

    public EventController(UserService userService, UserServiceImpl userServiceimp, EventService eventService, StageRepository stageRepository) {
        this.userService = userService;
        this.userServiceimp = userServiceimp;
        this.eventService = eventService;
        this.stageRepository = stageRepository;
    }

    @GetMapping("org/events")
    public String getAllEvents(Model model) {
        List<EventDTO> eventDTOs = eventService.getAllEvents();
        model.addAttribute("events", eventDTOs);
        model.addAttribute("eventsJson", new Gson().toJson(eventDTOs));
        return "events_Org";
    }

    @GetMapping("user/events")
    public String getAllEventsForUser(Model model) {
        List<EventDTO> eventDTOs = eventService.getAllEvents();
        model.addAttribute("events", eventDTOs);
        model.addAttribute("eventsJson", new Gson().toJson(eventDTOs));
        return "events_User";
    }

    @GetMapping("/user/profile")
    public String userProfile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userServiceimp.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        model.addAttribute("user", user);
        model.addAttribute("eventCount", eventService.countUserEvents(user));
        return "profile";
    }

    @GetMapping("/admin/events")
    public String getAllEventsForAdmin(Model model) {
        List<EventDTO> eventDTOs = eventService.getAllEvents();
        model.addAttribute("events", eventDTOs);
        model.addAttribute("eventsJson", new Gson().toJson(eventDTOs));
        return "events_Admin";
    }

    @PostMapping("/add-event")
    public String addEvent(@RequestParam String nameEvent, @RequestParam String date) {
        eventService.addEvent(nameEvent, date);
        return "redirect:/org/events";
    }

    @GetMapping("/user/event-details/{id}")
    public String getEventDetails(@PathVariable("id") Long eventId, Model model) {
        EventDTO eventDTO = eventService.getEventDTOById(eventId);
        model.addAttribute("event", eventDTO);
        System.out.println(eventDTO.toString());
        return "event-details_User";
    }

    @GetMapping("/admin/event-details/{id}")
    public String getEventDetailsForAdmin(@PathVariable("id") Long eventId, Model model) {
        model.addAttribute("stage",new Stage());
        EventDTO eventDTO = eventService.getEventDTOById(eventId);
        model.addAttribute("event", eventDTO);
        var stages = stageRepository.findAllByEventId(eventId);
        model.addAttribute("stages", stages);
        System.out.println(eventDTO.toString());
        return "event-details_Admin";
    }

    @PostMapping("/admin/event-details/{id}/apply")
    public String applyForEvent(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        eventService.addParticipant(id, username);
        return "redirect:/user/event-details/" + id;
    }

    @PostMapping("/admin/event/edit")
    public String editEvent(@ModelAttribute Event event, Model model) {
        Event events = eventService.save(event);
        model.addAttribute("event", events);
        return "redirect:/admin/event-details/" + event.getId();
    }

    @PostMapping("/admin/event/save")
    public String saveEvent(@ModelAttribute Event event) {
        eventService.save(event);
        return "redirect:/admin/event/" + event.getId() + "/edit";
    }

    @GetMapping("/admin/event/{id}/delete")
    public String deleteEvent(@PathVariable Long id) {
        eventService.delete(id);
        return "redirect:/admin/events";
    }

    @PostMapping("/admin/event/{id}/stage/save")
    public String saveStage(@PathVariable Long id, @ModelAttribute Stage stage) {
        eventService.saveStage(id, stage);
        return "redirect:/admin/event/" + id + "/edit";
    }

    @GetMapping("/admin/event/{eventId}/stage/{stageId}/delete")
    public String deleteStage(@PathVariable Long eventId, @PathVariable Long stageId) {
        eventService.deleteStage(eventId, stageId);
        return "redirect:/admin/event-details/" + eventId;
    }


    @PostMapping("/user/event-details/{id}/comment")
    public String addEventComment(@PathVariable Long id, @RequestParam String comment, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        eventService.addComment(id, user, comment);
        return "redirect:/user/event-details/" + id;
    }
    /*@PostMapping("/user/stage-details/{id}/comment")
    public String addStageComment(@PathVariable Long id, @RequestParam String comment, Principal principal) {
        String username = principal.getName();
        User user = userServiceimp.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        eventService.addStageComment(id, user, comment);
        return "redirect:/user/stage-details/" + id;
    }*/
}
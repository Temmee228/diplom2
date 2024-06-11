package com.example.diplom2.controller;

import com.example.diplom2.entity.User;
import com.example.diplom2.repository.UserRepository;
import com.example.diplom2.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "login"; // Имя шаблона страницы входа (login.html)
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration"; // Имя шаблона страницы регистрации (registration.html)
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute User user, @RequestParam("confirmPassword") String confirmPassword, Model model) {
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Пароли не совпадают");
            return "registration";
        }
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Имя пользователя уже занято");
            return "registration";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        return "redirect:/login";


    }
}


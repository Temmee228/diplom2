package com.example.diplom2.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

@Controller
public class ErrorController {

    @RequestMapping("/custom-error")
    public String handleError(HttpServletRequest request, Model model) {
        // Добавьте логику для получения кода статуса и передачи его в модель, если необходимо
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        HttpStatus httpStatus = HttpStatus.resolve(statusCode);
        model.addAttribute("code", statusCode);
        model.addAttribute("msg", httpStatus.getReasonPhrase());

        // Убедитесь, что errorPage не вызывает перенаправление
        return "errorPage";
    }
}


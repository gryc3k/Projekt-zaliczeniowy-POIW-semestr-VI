package com.mycompany.projektroboty;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

// Zmieniamy @RestController na @Controller, bo teraz zwracamy stronę HTML!
@Controller 
public class RobotController {

    private final RobotRepository repository;

    public RobotController(RobotRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String pokazWszystkieRoboty(Model model) {
        // Pobieramy z bazy
        List<Robot> roboty = repository.findAll();
        // Przekazujemy paczkę danych do pliku HTML pod nazwą "roboty"
        model.addAttribute("roboty", roboty); 
        // Spring Boot będzie szukał pliku o nazwie "index.html"
        return "index"; 
    }
}
package com.mycompany.projektroboty;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class RobotController {

    @Autowired
    private RobotRepository robotRepository;

    // ... Twoje obecne metody (np. @GetMapping("/") dla listy) ...

    // 1. Metoda do otwarcia formularza edycji
    @GetMapping("/edytuj/{id}")
    public String pokazFormularzEdycji(@PathVariable("id") Long id, Model model) {
        Robot robot = robotRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID: " + id));
        model.addAttribute("robot", robot);
        return "edytuj"; // Nazwa pliku edytuj.html w folderze templates
    }

    // 2. Metoda do zapisu (możesz użyć swojej obecnej metody @PostMapping("/dodaj"), 
    // jeśli ona obsługuje też update, albo stworzyć dedykowaną)
    @PostMapping("/zapisz")
    public String zapiszRobota(@ModelAttribute("robot") Robot robot) {
        robotRepository.save(robot); // Hibernate sam wykryje ID i wykona UPDATE zamiast INSERT
        return "redirect:/"; // Powrót na stronę główną po zapisie
    }
}
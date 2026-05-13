package com.mycompany.projektroboty;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class RobotController {

    private final RobotRepository repository;

    public RobotController(RobotRepository repository) {
        this.repository = repository;
    }

    // Główna strona: Lista + Formularz + Suma
    @GetMapping("/")
    public String index(Model model) {
        List<Robot> roboty = repository.findAll();
        
        // Obliczanie łącznej wartości magazynu (Logika biznesowa)
        double sumaWartosci = roboty.stream()
                .mapToDouble(r -> r.getCena() * r.getIlosc())
                .sum();

        model.addAttribute("roboty", roboty);
        model.addAttribute("nowyRobot", new Robot()); // Pusty obiekt do formularza
        model.addAttribute("sumaWartosci", sumaWartosci);
        
        return "index";
    }

    // Obsługa dodawania nowego robota
    @PostMapping("/dodaj")
    public String dodaj(@ModelAttribute Robot robot) {
        repository.save(robot);
        return "redirect:/"; // Po dodaniu odśwież stronę
    }

    // Obsługa usuwania robota po ID
    @GetMapping("/usun/{id}")
    public String usun(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}
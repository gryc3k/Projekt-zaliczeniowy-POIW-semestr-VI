package com.mycompany.projektroboty;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class RobotController {

    @Autowired
    private RobotRepository robotRepository;

    @GetMapping("/")
    public String pokazStroneGlowna(Model model) {
        List<Robot> listaRobotow = robotRepository.findAll();
        model.addAttribute("roboty", listaRobotow);
        model.addAttribute("robot", new Robot());

        // Obliczanie sumy: cena * ilość dla każdego robota
        double suma = listaRobotow.stream()
                .mapToDouble(r -> r.getCena() * r.getIlosc())
                .sum();

        model.addAttribute("sumaWartosci", suma); // To jest kluczowe!

        return "index";
    }

    @PostMapping("/dodaj")
    public String zapiszRobota(@ModelAttribute("robot") Robot robot) {
        robotRepository.save(robot);
        return "redirect:/";
    }

    @GetMapping("/edytuj/{id}")
    public String pokazFormularzEdycji(@PathVariable("id") Long id, Model model) {
        Robot robot = robotRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Nieprawidłowe ID: " + id));
        model.addAttribute("robot", robot);
        return "edytuj";
    }
    
    @GetMapping("/usun/{id}")
    public String usunRobota(@PathVariable("id") Long id) {
        robotRepository.deleteById(id);
        return "redirect:/";
    }
}
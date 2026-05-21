package com.mycompany.projektroboty;

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
        model.addAttribute("roboty", robotRepository.findAll());
        // Zmieniono: używamy nazwy "robot"
        model.addAttribute("robot", new Robot()); 
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
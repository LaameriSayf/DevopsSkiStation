package tn.esprit.ouday_oueslati_4TWIN5.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Bienvenue à l’API Ski Station ! Accédez aux skieurs via /skier/get/{numSkier}";
    }
}
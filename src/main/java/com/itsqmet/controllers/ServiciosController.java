package com.itsqmet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiciosController {
    @GetMapping("/servicios")
    public String service() {
        return "pages/servicios";
    }
}

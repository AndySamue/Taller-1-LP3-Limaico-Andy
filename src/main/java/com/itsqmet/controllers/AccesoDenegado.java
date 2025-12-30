package com.itsqmet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccesoDenegado {
    @GetMapping("/acceso-denegado")
    public String accesoDenegado () {
        return "pages/accesoDenegado";
    }
}

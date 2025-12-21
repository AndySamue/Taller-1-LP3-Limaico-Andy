package com.itsqmet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/inicio")
    public String home() {
        return "index";
    }
}

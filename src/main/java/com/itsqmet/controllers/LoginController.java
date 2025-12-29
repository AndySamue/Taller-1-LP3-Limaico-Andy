package com.itsqmet.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "pages/login";
    }

    // Indicar a donde se dirige un usuario después de la autenticación
    @GetMapping("/postLogin")
    public String dirigirPorRol(Authentication authentication){
        // Tiene el objeto del usuario quien acaba de iniciar sesión exitosamente
        User usuario = (User) authentication.getPrincipal();
        // Procesa la lista de roles o permisos del usuario para obtener el primero como un string
        String role = usuario.getAuthorities().stream()
                // Extraer el nombre del rol (ROLE_ADMIN) de cada objeto de autoridad
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                // Tomar el primer rol que encuentra en la lista
                .findFirst()
                // Si el usuario no tiene ningun rol, devuelve vacio
                .orElse("");

        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else if (role.equals("ROLE_CLIENTE")) {
            return "redirect:/contacto/formContacto";
        }  

        // Si el rol no coincide
        return "redirect:/login?error";

    }

}

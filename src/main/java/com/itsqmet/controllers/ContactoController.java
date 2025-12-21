package com.itsqmet.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsqmet.model.Contacto;
import com.itsqmet.service.ContactoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/contacto")

public class ContactoController {

    @Autowired
    private final ContactoService contactoService;

    ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    // Visualizar Registro
    @GetMapping
    public String mostrarRegistro(Model model) {
        List<Contacto> contactos = contactoService.mostrarRegistro();
        model.addAttribute("contactos", contactos);
        return "pages/registroExitoso";
    }

    // Mostrar formulario
    @GetMapping("/formContacto")
    public String formularioContacto(org.springframework.ui.Model model) {
        Contacto contacto = new Contacto();
        model.addAttribute("contacto", contacto);
        return "pages/contactoForm";
    }

    // Enviar datos del formulario a la BD
    @PostMapping("/registrarCliente")
    public String guardarCliente(
            @Valid @ModelAttribute Contacto contacto,
            org.springframework.validation.BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            // Volver al formulario si hay errores
            return "pages/contactoForm";
        }

        contactoService.guardarContacto(contacto);
        return "redirect:/contacto";
    }

    // Actualizar
    @GetMapping("/editar/{id}")
    public String actualizarContacto (@PathVariable Long id, Model model){
        Optional<Contacto>contacto = contactoService.buscarContactoById(id);
        model.addAttribute("contacto",contacto);
        return "pages/contactoForm";
    }

    // Eliminar producto
    @DeleteMapping("/eliminarContacto/{id}")
    public String eliminarContacto(@PathVariable Long id){
        contactoService.eliminarRegistro(id);
        return "redirect:/contacto";
    }

}

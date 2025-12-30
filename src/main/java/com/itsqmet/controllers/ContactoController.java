package com.itsqmet.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.itsqmet.model.Contacto;
import com.itsqmet.model.Usuario;
import com.itsqmet.roles.Rol;
import com.itsqmet.service.ContactoService;
import com.itsqmet.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/contacto")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String mostrarRegistro(
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        Usuario usuario = usuarioService
                .buscarPorUsername(userDetails.getUsername());

        List<Contacto> contactos;

        if (usuario.getRol() == Rol.ROLE_ADMIN) {
            contactos = contactoService.mostrarRegistro();
        } else {
            contactos = contactoService.mostrarPorUsuario(usuario.getId());
        }

        model.addAttribute("contactos", contactos);
        return "pages/registroExitoso";
    }

    @GetMapping("/formContacto")
    public String formularioContacto(Model model) {
        model.addAttribute("contacto", new Contacto());
        return "pages/contactoForm";
    }

    @PostMapping("/registrarCliente")
    public String guardarCliente(
            @Valid @ModelAttribute Contacto contacto,
            BindingResult result,
            @AuthenticationPrincipal UserDetails userDetails) {

        if (result.hasErrors()) {
            return "pages/contactoForm";
        }

        Usuario usuario = usuarioService
                .buscarPorUsername(userDetails.getUsername());

        contacto.setUsuario(usuario);

        contactoService.guardarContacto(contacto);
        return "redirect:/contacto";
    }

    @GetMapping("/editar/{id}")
    public String actualizarContacto(
            @PathVariable Long id,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        Contacto contacto = contactoService.buscarContactoById(id)
                .orElseThrow(() -> new RuntimeException("Registro no existe"));

        Usuario usuario = usuarioService
                .buscarPorUsername(userDetails.getUsername());

        if (usuario.getRol() != Rol.ROLE_ADMIN &&
                !contacto.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/acceso-denegado";
        }

        model.addAttribute("contacto", contacto);
        return "pages/contactoForm";
    }

    @DeleteMapping("/eliminarContacto/{id}")
    public String eliminarContacto(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        Contacto contacto = contactoService.buscarContactoById(id)
                .orElseThrow(() -> new RuntimeException("Registro no existe"));

        Usuario usuario = usuarioService
                .buscarPorUsername(userDetails.getUsername());

        if (usuario.getRol() != Rol.ROLE_ADMIN &&
                !contacto.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/acceso-denegado";
        }

        contactoService.eliminarRegistro(id);
        return "redirect:/contacto";
    }
}

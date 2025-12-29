package com.itsqmet.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itsqmet.model.Usuario;
import com.itsqmet.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Leer ---
    @GetMapping
    public String mostrarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.mostrarUsuario();
        model.addAttribute("usuarios", usuarios);
        return "pages/listaUsuario";
    }

    // Guardar ---
    // 1. Mostrar Formulario
    @GetMapping("/formUsuario")
    public String formularioUsuario (Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("usuario",usuario);
        return "pages/usuarioForm";
    }

    // Enviar los datos ingresados en el form a la DB
    @PostMapping("/registrarUsuario")
    public String guardarUsuario(Usuario usuario){
        if (usuario.getId() !=null){
            usuarioService.actualizarUsuario(usuario.getId(), usuario);
        } else {
            usuarioService.guardarUsuario(usuario);
        }
        return "redirect:/usuarios";
    }

    // Actualizar
    @GetMapping("/editar/{id}")
    public String actualizarUsuario (@PathVariable Long id, Model model){
        Optional<Usuario> usuario = usuarioService.buscaUsuarioById(id);
        model.addAttribute("usuario",usuario);
        return "pages/usuarioForm";
    }

    // Delete
    // Eliminar producto
    @DeleteMapping("/eliminarUsuario/{id}")
    public String eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
        return "redirect:/usuarios";
    }


}   

package com.itsqmet.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.itsqmet.model.Contacto;
import com.itsqmet.repository.ContactoRepository;

@Service
public class ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    // Visualizar
    public List<Contacto> mostrarRegistro() {
        return contactoRepository.findAll();
    }

    // Visualizar contactos por usuario (CLIENTE)
    public List<Contacto> mostrarPorUsuario(Long usuarioId) {
        return contactoRepository.findByUsuarioId(usuarioId);
    }

    // Guardar
    public Contacto guardarContacto(Contacto contacto) {
        contactoRepository.save(contacto);
        return contacto;
    }

    // Buscar por ID
    public Optional<Contacto> buscarContactoById(Long id) {
        return contactoRepository.findById(id);
    }

    // Actualizar
    public Contacto actualizarContacto(Long id, Contacto contacto) {
        Contacto contactoExistente = buscarContactoById(id)
                .orElseThrow(() -> new RuntimeException("Registro no existe"));
        contactoExistente.setNombre(contacto.getNombre());
        contactoExistente.setTelefono(contacto.getTelefono());
        contactoExistente.setEmail(contacto.getEmail());
        contactoExistente.setTipoServicio(contacto.getTipoServicio());
        contactoExistente.setMensaje(contacto.getMensaje());
        return contactoRepository.save(contactoExistente);
    }

    // Eliminar
    public void eliminarRegistro(Long id) {
        Contacto contacto = buscarContactoById(id)
                // Se lanza cuando no se encuentra el contacto
                .orElseThrow(() -> new ResponseStatusException(
                        // Constante de Spring que representa el código HTTP 404
                        // 404 = recurso no encontrado
                        HttpStatus.NOT_FOUND, "Registro no existe"));
        contactoRepository.delete(contacto);
    }

}

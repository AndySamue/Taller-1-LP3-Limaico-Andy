package com.itsqmet.service;

import com.itsqmet.model.Usuario;
import com.itsqmet.repository.UsuarioRepository;
import com.itsqmet.roles.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Leer
    public List<Usuario> mostrarUsuario() {
        return usuarioRepository.findAll();
    }

    // Buscar por ID
    public Optional<Usuario> buscaUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    // Guardar
    public Usuario guardarUsuario(Usuario usuario) {
        // Encriptar contraseña antes de guardar el usuario
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);
        // Asignar el rol de cliente por default a todos los usuarios que se registren
        usuario.setRol(Rol.ROLE_CLIENTE);
        return usuarioRepository.save(usuario) ;
    }

    // Actualizar
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioExistente = buscaUsuarioById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setUsername(usuario.getUsername());
        // Actualización del password solo
        if (usuario.getPassword()!= null && !usuario.getPassword().trim().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        return usuarioRepository.save(usuarioExistente);
    }

    // Eliminar
    public void eliminarUsuario(Long id) {
        Usuario usuario = buscaUsuarioById(id)
                // Se lanza cuando no se encuentra el libro
                .orElseThrow(() -> new ResponseStatusException(
                        // Constante de Spring que representa el código HTTP 404
                        // 404 = recurso no encontrado
                        HttpStatus.NOT_FOUND, "Usuario no existe"
                ));
        usuarioRepository.delete(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Buscar en la BD al usuario que esta autenticandose, si no existe se lanza una exceptión
        // para denegar el acceso

        Usuario usuario = usuarioRepository.findByUsername (username)
                .orElseThrow (()-> new UsernameNotFoundException("Usuario no encontrado"));

        // Usar el metodo builder para construir el objeto que SS entiende como usuario autenticado
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(usuario.getRol().name())
                .build();

    }
}

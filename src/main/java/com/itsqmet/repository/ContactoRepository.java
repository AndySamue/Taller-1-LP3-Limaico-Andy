package com.itsqmet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itsqmet.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
     List<Contacto> findByUsuarioId(Long usuarioId);
}

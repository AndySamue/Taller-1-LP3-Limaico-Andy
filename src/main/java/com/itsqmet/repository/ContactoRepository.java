package com.itsqmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itsqmet.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}

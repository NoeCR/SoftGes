package com.softgest.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softgest.app.models.entity.UsuarioDetalle;

@Repository
public interface IUsuarioDetalleDao extends JpaRepository<UsuarioDetalle, Long> {

	UsuarioDetalle findByUsuarioId(Long usuario_id);
}

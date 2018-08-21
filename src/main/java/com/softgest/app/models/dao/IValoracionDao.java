package com.softgest.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softgest.app.models.entity.Valoracion;

@Repository
public interface IValoracionDao extends JpaRepository<Valoracion, Long> {

	List<Valoracion> findByProductoId(Long producto_id);
	List<Valoracion> findByUsuarioId(Long usuario_id);
}

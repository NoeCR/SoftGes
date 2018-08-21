package com.softgest.app.models.service;

import java.util.List;

import com.softgest.app.models.entity.Valoracion;

public interface IValoracionService {

	public List<Valoracion> findByProducto(Long producto_id);
	public List<Valoracion> findByUsuario(Long usuario_id);
	public void saveValoracion(Valoracion valoracion);
}

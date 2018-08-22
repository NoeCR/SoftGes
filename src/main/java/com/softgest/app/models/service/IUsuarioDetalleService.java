package com.softgest.app.models.service;

import com.softgest.app.models.entity.UsuarioDetalle;


public interface IUsuarioDetalleService {

	public UsuarioDetalle findByUsuario(Long usuario_id);
	public void saveUsuarioDetalle(UsuarioDetalle usuarioDetalle);
}

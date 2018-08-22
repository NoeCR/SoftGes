package com.softgest.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softgest.app.models.dao.IUsuarioDetalleDao;
import com.softgest.app.models.entity.UsuarioDetalle;

@Service("usuarioDetalleServiceImp")
public class UsuarioDetalleServiceImp implements IUsuarioDetalleService {

	@Autowired
	private IUsuarioDetalleDao usuarioDetalleDao;
	@Override
	public UsuarioDetalle findByUsuario(Long usuario_id) {		
		return usuarioDetalleDao.findByUsuarioId(usuario_id);
	}
	@Override
	public void saveUsuarioDetalle(UsuarioDetalle usuarioDetalle) {
		usuarioDetalleDao.save(usuarioDetalle);		
	}

}

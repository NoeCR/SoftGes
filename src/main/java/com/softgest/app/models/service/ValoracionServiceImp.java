package com.softgest.app.models.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softgest.app.models.dao.IValoracionDao;
import com.softgest.app.models.entity.Valoracion;

@Service("valoracionServiceImp")
public class ValoracionServiceImp implements IValoracionService {

	@Autowired
	private IValoracionDao valoracionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Valoracion> findByProducto(Long producto_id) {
		return valoracionDao.findByProductoId(producto_id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Valoracion> findByUsuario(Long usuario_id) {
		return valoracionDao.findByUsuarioId(usuario_id);
	}

	@Override
	@Transactional
	public void saveValoracion(Valoracion valoracion) {
		valoracionDao.save(valoracion);		
	}

}

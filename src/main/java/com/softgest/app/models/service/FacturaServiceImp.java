package com.softgest.app.models.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softgest.app.models.dao.IFacturaDao;
import com.softgest.app.models.entity.Factura;


@Service
public class FacturaServiceImp implements IFacturaService {

	@Autowired
	private IFacturaDao facturaDao;
	
	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Factura> findByUsuarioId(Long usuario_id) {
		return facturaDao.findByUsuarioId(usuario_id);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findById(Long factura_id) {
		return facturaDao.findById(factura_id).orElse(null);
	}

}

package com.softgest.app.models.service;



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

}

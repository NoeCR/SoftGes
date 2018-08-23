package com.softgest.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softgest.app.models.dao.IFacturaDao;
import com.softgest.app.models.entity.Factura;

@Service
public class FacturaServiceImp implements IFacturaService {

	@Autowired
	private IFacturaDao facturaDao;
	
	@Override
	public void savefactura(Factura factura) {
		facturaDao.save(factura);
	}

}

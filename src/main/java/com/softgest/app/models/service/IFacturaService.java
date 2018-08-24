package com.softgest.app.models.service;

import java.util.List;

import com.softgest.app.models.entity.Factura;


public interface IFacturaService {

	public void saveFactura(Factura factura);
	
	public List<Factura> findByUsuarioId(Long usuario_id);
	
	public Factura findById(Long factura_id);
}

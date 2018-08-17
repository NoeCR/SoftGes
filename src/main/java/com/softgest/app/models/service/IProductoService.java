package com.softgest.app.models.service;

import java.util.List;
import com.softgest.app.models.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll();
	public List<Producto> findByCategoria(Long categoria_id);
	public Producto findById(Long producto_id);
}

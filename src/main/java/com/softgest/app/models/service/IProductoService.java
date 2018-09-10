package com.softgest.app.models.service;

import java.util.List;
import com.softgest.app.models.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll();
	public List<Producto> findByCategoria(Long categoria_id);
	public Producto findById(Long producto_id);
	public List<Producto> findByNombre(String term);
	public List<Producto> findByEstado(String term);
	public void saveProducto(Producto producto);
}

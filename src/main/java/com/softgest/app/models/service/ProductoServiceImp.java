package com.softgest.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softgest.app.models.dao.IProductoDao;
import com.softgest.app.models.entity.Producto;

@Service("productoServiceImp")
public class ProductoServiceImp implements IProductoService {

	@Autowired
	private IProductoDao productoDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Producto> findAll() {		
		return productoDao.findAll();
	}

	@Override
	public List<Producto> findByCategoria(Long categoria_id) {		
		return productoDao.findByCategoriaId(categoria_id);
	}

}

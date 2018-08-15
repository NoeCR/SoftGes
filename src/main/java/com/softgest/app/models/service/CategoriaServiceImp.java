package com.softgest.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softgest.app.models.dao.ICategoriaDao;
import com.softgest.app.models.entity.Categoria;

@Service("categoriaServiceImp")
public class CategoriaServiceImp implements ICategoriaService {

	@Autowired
	private ICategoriaDao categoriaDao;
	
	@Override
	public List<Categoria> findAll() {		
		return categoriaDao.findAll();
	}

}

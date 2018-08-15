package com.softgest.app.models.service;

import java.util.List;

import com.softgest.app.models.entity.Categoria;

public interface ICategoriaService {

	List<Categoria> findAll();
}

package com.softgest.app.models.service;

import java.util.List;

import com.softgest.app.models.entity.Usuario;

public interface IUsuarioService {

	void insertar(Usuario usuario); 
	List<Usuario> buscarTodos();
	List<Usuario> buscarActivos();
	void eliminar(Long idUsuario);
	Usuario buscarPorId(Long idUsuario);
}

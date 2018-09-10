package com.softgest.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.softgest.app.models.entity.Usuario;

public interface IUsuarioService {

	void insertar(Usuario usuario); 
	List<Usuario> buscarTodos();
	List<Usuario> buscarActivos();
	void eliminar(Long idUsuario);
	Usuario buscarPorId(Long idUsuario);
	Usuario findByName(String nombre);
	public Page<Usuario> findAll(Pageable pageable);
}
/*
public List<Usuario> findAll();

public Page<Usuario> findAll(Pageable pageable);

public void save(Usuario usuario);



public Usuario fetchByIdWithFacturas(Long id);

public void delete(Long id);

public List<Producto> findByNombre(String term);

public void saveFactura(Factura factura);

public Producto findProductoById(Long id);

public Factura findFacturaById(Long id);

public void deleteFactura(Long id);

public Factura fetchFacturaByIdWithClienteWhithItemFacturaWithProducto(Long id);
*/
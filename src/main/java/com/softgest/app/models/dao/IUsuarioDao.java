package com.softgest.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softgest.app.models.entity.Usuario;

@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, Long>{

	
	public Usuario findByUsername(String username);	
	public List<Usuario> findByEnabled(int activo);
}

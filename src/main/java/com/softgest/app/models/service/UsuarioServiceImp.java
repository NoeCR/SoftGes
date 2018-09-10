package com.softgest.app.models.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.softgest.app.models.dao.IUsuarioDao;
import com.softgest.app.models.entity.Role;
import com.softgest.app.models.entity.Usuario;

@Service("usuarioServiceImp")
public class UsuarioServiceImp implements UserDetailsService,IUsuarioService{

	@Autowired
	private IUsuarioDao usuarioDao;	
	
	private Logger logger = LoggerFactory.getLogger(UsuarioServiceImp.class);
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDao.findByUsername(username);
		
		if(usuario == null) {
			logger.error("Error login: no existe el usuario '" + username + "'");
			throw new UsernameNotFoundException("Username" + username + " no existe en el sistema!");
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Role rol : usuario.getRoles() ) {
			authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
		}
		if(authorities.isEmpty()) {
			logger.error("Error login: usuario '" + username + "' no tiene roles asignados");
			throw new UsernameNotFoundException("Username" + username + " no existe en el sistema!");
		}
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.isEnabled(), true, true, true, authorities);
	}

	@Override
	public void insertar(Usuario usuario) {
		usuarioDao.save(usuario);
		
	}

	@Override
	public List<Usuario> buscarTodos() {
		return usuarioDao.findAll();
	}

	@Override
	public List<Usuario> buscarActivos() {		
		return usuarioDao.findByEnabled(1);
	}

	@Override
	public void eliminar(Long idUsuario) {
		usuarioDao.deleteById(idUsuario);
		
	}

	@Override
	public Usuario buscarPorId(Long idBanner) {
		Optional<Usuario> optional = usuarioDao.findById(idBanner);
		if(optional.isPresent()) {
			return optional.get();
		}		
		return null;
	}

	@Override
	public Usuario findByName(String nombre) {		
		return usuarioDao.findByUsername(nombre);
	}

	@Override
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}
}

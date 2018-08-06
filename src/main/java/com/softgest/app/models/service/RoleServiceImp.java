package com.softgest.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softgest.app.models.dao.IRoleDao;
import com.softgest.app.models.entity.Role;

@Service("roleServiceImp")
public class RoleServiceImp implements IRoleService {

	@Autowired
	private IRoleDao roleDao;
	
	@Override
	public void insert(Role rol) {		
		roleDao.save(rol);
	}

	@Override
	public void update(Role rol) {
		roleDao.save(rol);
	}

	@Override
	public void delete(Role rol) {
		roleDao.delete(rol);
	}

}

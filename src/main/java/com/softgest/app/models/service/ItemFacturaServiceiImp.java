package com.softgest.app.models.service;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softgest.app.models.dao.IItemFacturaDao;
import com.softgest.app.models.entity.ItemFactura;

@Service
public class ItemFacturaServiceiImp implements IItemFacturaService {

	@Autowired
	private IItemFacturaDao itemFacturaDao;
	
	@Override
	@Transactional
	public void saveItemFactura(ItemFactura itemFactura) {
		itemFacturaDao.save(itemFactura);
	}

}

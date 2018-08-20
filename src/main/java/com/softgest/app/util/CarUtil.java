package com.softgest.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

import com.softgest.app.models.entity.ItemFactura;
import com.softgest.app.models.entity.Producto;

public class CarUtil {

	private final static Logger logger = Logger.getLogger("info");
	
	public static List<ItemFactura> addProducto(Producto producto, HttpSession session){
		ItemFactura item = new ItemFactura(producto);
		logger.log(Level.INFO, item.toString());
		boolean encontrado = false;
		@SuppressWarnings("unchecked")
		List<ItemFactura> items = (List<ItemFactura>) session.getAttribute("items");		
		
		if(items == null) {
			items = new ArrayList<ItemFactura>();
			items.add(item);
			return items;
		}
		for(ItemFactura itm: items) {
			if(itm.getProducto().getId() == item.getProducto().getId()) {
				itm.addCantidad();
				encontrado = true;	
			}
		}
		if(!encontrado) {
			items.add(item);
			return items;
		}else {
			return items;
		}
	}
}

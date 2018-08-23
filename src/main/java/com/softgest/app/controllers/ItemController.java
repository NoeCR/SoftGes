package com.softgest.app.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.softgest.app.models.entity.Factura;
import com.softgest.app.models.entity.ItemFactura;
import com.softgest.app.models.entity.Producto;
import com.softgest.app.models.service.IProductoService;
import com.softgest.app.util.CarUtil;

@Controller
@RequestMapping("/car")
@SessionAttributes("carro")
public class ItemController {
	
	@Autowired
	private IProductoService productoService;

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	@GetMapping(value="/{productoId}")
	public String addProducto(@PathVariable(value="productoId") Long producto_id, HttpSession session) {
		Producto producto = productoService.findById(producto_id);		
		logger.info("Información del producto: ".concat(producto.toString()) );
		List<ItemFactura> items = CarUtil.addProducto(producto,session);
		session.setAttribute("items", items);		
		return "redirect:/";
	}
	
	@GetMapping(value="/remove/{productoId}")
	public String removeProducto(@PathVariable(value="productoId") Long producto_id, HttpSession session) {
		Factura factura = (Factura) session.getAttribute("factura");		
		List<ItemFactura> items = factura.getItems();	
		
		List<ItemFactura> itemsFactura = CarUtil.removeProducto(items, producto_id);
		session.setAttribute("items", itemsFactura);		
		return "redirect:/cart/";
	}
}

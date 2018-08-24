package com.softgest.app.controllers;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.softgest.app.models.entity.Factura;
import com.softgest.app.models.entity.ItemFactura;

import com.softgest.app.models.entity.Usuario;
import com.softgest.app.models.service.IFacturaService;
import com.softgest.app.util.CalculoLinea;

@Controller
@SessionAttributes("factura") 
public class FacturaController {
	
	@Autowired
	private IFacturaService facturaService;
	
	//@Autowired
	//private IProductoService productoService;
	
	//@Autowired
	//private IItemFacturaService itemFacturaService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@GetMapping(value="/cart/")
	public String crearFactura(Map<String, Object> model, RedirectAttributes flash, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		@SuppressWarnings("unchecked")
		List<ItemFactura> items = (List<ItemFactura>) session.getAttribute("items");
		Factura factura = new Factura();
		// Si no hay cliente se le redirecciona a la pagina de login
		if(usuario == null) {
			flash.addFlashAttribute("warning", "Debe estar registrado para realizar la compra.");
			return "redirect:/login";
		}else {		
			factura.setUsuario(usuario);
			if(usuario.getItems() != null) {			
				factura.setItems(usuario.getItems());
			}
		}		
		
		if(!factura.getItems().isEmpty()) {
			for(ItemFactura itm : factura.getItems()) {
				if(itm.getProducto().getId() != null) {
					logger.info("Información de los items de la LINIA DE FACTURA: ".concat(itm.toString()));					
				}
			}
		}
		session.setAttribute("factura", factura);
		model.put("factura", factura);
		model.put("usuario", usuario);
		if(items != null) {
			int numItems = items.size();
			model.put("numItems", numItems);
		}
		model.put("titulo", "Realizar compra");
		
		return "usuario/verCarro";
	}
	
	@PostMapping(value="/cart/save/")
	public String guardarFactura(Map<String, Object> model, 
									RedirectAttributes flash, 
									HttpSession session,
									SessionStatus status) {
		
		Factura factura = (Factura) session.getAttribute("factura");		
		
		for(ItemFactura item:  factura.getItems()) {
			item.setLineaTotal(CalculoLinea.calculoLinea(item.getCantidad(), item.getProducto().getPrecio()));
			
			
		}
		factura.setFacturaTotal(CalculoLinea.total);
		CalculoLinea.resetTotal();
		facturaService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success", "Factura creada con éxito!");
		
		return "redirect:/";
	}
}

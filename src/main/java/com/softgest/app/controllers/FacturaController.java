package com.softgest.app.controllers;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.softgest.app.models.entity.Factura;
import com.softgest.app.models.entity.ItemFactura;
import com.softgest.app.models.entity.Producto;
import com.softgest.app.models.entity.Usuario;
import com.softgest.app.models.service.IFacturaService;
import com.softgest.app.models.service.IProductoService;
import com.softgest.app.models.service.IUsuarioService;
import com.softgest.app.util.CalculoLinea;

@Controller
@SessionAttributes("factura") 
public class FacturaController {
	
	@Autowired
	private IFacturaService facturaService;
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
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
		@SuppressWarnings("unchecked")
		List<ItemFactura> items = (List<ItemFactura>) session.getAttribute("items");
		if(factura.getItems().isEmpty()) {
			model.put("titulo", "Realizar compra");
			model.put("error", "Error: La factura NO puede no tener productos!");
			return "redirect:/cart/";
		}
		for(ItemFactura item:  factura.getItems()) {
			item.setLineaTotal(CalculoLinea.calculoLinea(item.getCantidad(), item.getProducto().getPrecio()));
			Producto p = item.getProducto();
			if((p.getStock() - item.getCantidad()) < 0) {
				flash.addFlashAttribute("error", "Lo sentimos pero no qudan suficientes unidades del producto: " + item.getProducto().getNombre());
				CalculoLinea.resetTotal();
				return "redirect:/cart/";
			}else {
				p.restStock(item.getCantidad());
				productoService.saveProducto(p);
			}
			
		}
		factura.setFacturaTotal(CalculoLinea.total);
		CalculoLinea.resetTotal();
		facturaService.saveFactura(factura);
		items.clear();
		status.setComplete();
		flash.addFlashAttribute("success", "Factura creada con éxito!");
		
		return "redirect:/";
	}
	
	@GetMapping(value = "/usuario/verFacturas/{id}")
	public String verFacturas(@PathVariable(value="id") Long usuario_id, Map<String, Object> model, RedirectAttributes flash, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		@SuppressWarnings("unchecked")
		List<ItemFactura> items = (List<ItemFactura>) session.getAttribute("items");
		if(items != null) {
			int numItems = items.size();
			model.put("numItems", numItems);
		}else {
			int numItems = 0;
			model.put("numItems", numItems);
		}
		List<Factura> facturas =  facturaService.findByUsuarioId(usuario_id);
		if(facturas.isEmpty()) {
			flash.addAttribute("warning", "No tiene ninguna factura.");
			return "/usuario/verFacturas";
		}
		Usuario cliente = usuarioService.buscarPorId(usuario_id);
		cliente.setFacturas(facturas);
		logger.info("Informacion del Cliente y sus facturas: " + cliente.toString());
		model.put("cliente", cliente);
		model.put("usuario", usuario);
		return "/usuario/verFacturas";
	}
	
	@GetMapping(value = "/usuario/verDetalleFactura/{id}")
	public String verDetalleFactura(@PathVariable(value="id") Long factura_id, Map<String, Object> model, RedirectAttributes flash, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		Factura factura = facturaService.findById(factura_id);
		@SuppressWarnings("unchecked")
		List<ItemFactura> items = (List<ItemFactura>) session.getAttribute("items");
		if(items != null) {
			int numItems = items.size();
			model.put("numItems", numItems);
		}else {
			int numItems = 0;
			model.put("numItems", numItems);
		}
		List<ItemFactura> itemsfactura = factura.getItems();
		model.put("items", itemsfactura);
		model.put("factura", factura);
		model.put("usuario", usuario);
		return "/usuario/verDetalleFactura";
	}
}

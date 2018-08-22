package com.softgest.app.controllers;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softgest.app.models.entity.Factura;
import com.softgest.app.models.entity.ItemFactura;
import com.softgest.app.models.entity.Usuario;



@Controller
@RequestMapping("/factura")
@SessionAttributes("factura") 
public class FacturaController {
	
	//@Autowired
	//private IUsuarioService usuarioService;
	
	//@Autowired
	//private IProductoService productoService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@GetMapping(value="/form/{usuarioid}")
	public String crearFactura(@PathVariable(value="usuarioid") Long usuarioId, Map<String, Object> model, RedirectAttributes flash, HttpSession session) {
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
		//session.setAttribute("factura", factura);
		model.put("factura", factura);
		model.put("usuario", usuario);
		if(items != null) {
			int numItems = items.size();
			model.put("numItems", numItems);
		}
		model.put("titulo", "Realizar compra");
		
		return "usuario/verCarro";
	}
	/*
	@GetMapping(value = "/cargar-productos/",path="/cargar-productos/", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(HttpSession session) {
		 List<Producto> productos = new ArrayList<Producto>();
		 Factura factura = (Factura) session.getAttribute("factura");
		 List<ItemFactura> items = factura.getItems();
		 for(int i = 0; i < items.size(); i++) {
			 productos.add(items.get(i).getProducto());
		 }
		return productos;
	}
	*/
}

package com.softgest.app.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softgest.app.models.entity.ItemFactura;
import com.softgest.app.models.entity.Producto;
import com.softgest.app.models.entity.Usuario;
import com.softgest.app.models.entity.Valoracion;
import com.softgest.app.models.service.IProductoService;
import com.softgest.app.models.service.IValoracionService;

@Controller
public class ProductoController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IValoracionService valoracionService;
	
	@GetMapping(value="/verProducto/{id}")
	public String verProducto(Model model, HttpSession session, @PathVariable(value="id") Long id, RedirectAttributes flash) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		Producto producto = productoService.findById(id);	
		@SuppressWarnings("unchecked")
		List<ItemFactura> items = (List<ItemFactura>) session.getAttribute("items");
		if(items != null) {
			int numItems = items.size();
			model.addAttribute("numItems", numItems);
		}
		if(producto == null) {
			flash.addFlashAttribute("error", "Error: no ha podido cargarse el producto");
			return "redirect:/";
		}
		List<Valoracion> valoraciones = valoracionService.findByProducto(producto.getId());
		model.addAttribute("producto", producto);
		model.addAttribute("valoraciones", valoraciones);
	return "/producto/verProducto";	
	}
	
	@PostMapping(value="/saveValor")
	public String guardarValoracion(@Valid Valoracion valoracion, Model model, HttpSession session, RedirectAttributes flash, SessionStatus status) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		List<Valoracion> valoraciones = valoracionService.findByProducto(valoracion.getProducto().getId());
		valoracion.setUsuario(usuario);
		if(valoracion != null) {
			valoracionService.saveValoracion(valoracion);
		}
		status.setComplete();
		flash.addFlashAttribute("info", "Gracias por dejar su comentario.");
		model.addAttribute("usuario", usuario);
		model.addAttribute("producto", valoracion.getProducto());
		model.addAttribute("valoraciones", valoraciones);
		return "redirect:/verProducto/".concat(valoracion.getProducto().getId().toString());	
	}
}

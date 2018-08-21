package com.softgest.app.controllers;

import javax.servlet.http.HttpSession;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softgest.app.models.entity.Producto;
import com.softgest.app.models.entity.Usuario;
import com.softgest.app.models.service.IProductoService;

@Controller
public class ProductoController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IProductoService productoService;
	
	@GetMapping(value="/verProducto/{id}")
	public String verProducto(Model model, HttpSession session, @PathVariable(value="id") Long id, RedirectAttributes flash) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		Producto producto = productoService.findById(id);
		if(producto == null) {
			flash.addFlashAttribute("error", "Error: no ha podido cargarse el producto");
			return "redirect:/";
		}
		model.addAttribute("producto", producto);
	return "verProducto";	
	}
}

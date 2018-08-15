package com.softgest.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softgest.app.models.entity.Categoria;
import com.softgest.app.models.entity.Producto;
import com.softgest.app.models.service.ICategoriaService;
import com.softgest.app.models.service.IProductoService;

@Controller
public class IndexController {

	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@RequestMapping(value="/", method= RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("titulo", "Página Principal");
		List<Producto> productos = productoService.findAll();
		model.addAttribute("prodcutos", productos);
		List<Categoria> categorias = categoriaService.findAll();
		model.addAttribute("categorias", categorias);
		return "index";
	}
	
	@RequestMapping(value="/{categoriaid}", method= RequestMethod.GET)
	public String showProductByCat(Model model, @PathVariable(value="categoriaid") Long categoria_id, @RequestParam(value="error", required= false) String error, RedirectAttributes flash) {
		if(error != null) {
			model.addAttribute("error", "Error: no se encuentra ninguna categoria con este id");
		}
		model.addAttribute("titulo", "Página Principal");
		List<Producto> productos = productoService.findByCategoria(categoria_id); 
		if(productos.isEmpty()) {
			model.addAttribute("warning", "No hay productos para esta categoria");
		}
		model.addAttribute("prodcutos", productos);
		List<Categoria> categorias = categoriaService.findAll();
		model.addAttribute("categorias", categorias);
		return "index";
	}
}

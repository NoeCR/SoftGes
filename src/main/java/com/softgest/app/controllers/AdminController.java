package com.softgest.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softgest.app.models.entity.Categoria;
import com.softgest.app.models.entity.Producto;
import com.softgest.app.models.entity.Usuario;
import com.softgest.app.models.service.ICategoriaService;
import com.softgest.app.models.service.IProductoService;
import com.softgest.app.models.service.IUsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired 
	private IUsuarioService usuarioService;
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/", method= RequestMethod.GET)
	public String mainAdmin(Model model, HttpSession session) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		return "/admin/panel-gestion";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value="/listar-clientes")
	public String listarClientes(Model model, HttpSession session) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		model.addAttribute("clientes", usuarioService.buscarTodos());
		
		return "/admin/listado-clientes";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value="/listar-productos")
	public String listarProductos(Model model, HttpSession session) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		model.addAttribute("productos", productoService.findAll());
		
		return "/admin/listado-productos";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value="/crearProducto")
	public String crearProducto(Model model, HttpSession session) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		List<Categoria> categorias = categoriaService.findAll();
		model.addAttribute("categorias", categorias);
		return "/admin/formProducto";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping(value="/saveProduct")
	public String guardarProducto(@Valid Producto producto, @RequestParam("file") MultipartFile img, BindingResult result, Model model, HttpSession session, RedirectAttributes flash, SessionStatus status) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		logger.info("Información del Producto: ".concat(producto.toString()));
		if(result.hasErrors()) {
			return "/admin/formProducto";
		}
		if(!img.isEmpty()) {
			Path dirRecursos = Paths.get("src//main//resources//static/uploads");
			String rootPath = dirRecursos.toFile().getAbsolutePath();
			try {
				byte[] bytes = img.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + img.getOriginalFilename());
				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info", "Imagen subida correctamente '" + img.getOriginalFilename() + "'");
				producto.setImg(img.getOriginalFilename());
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}
		productoService.saveProducto(producto);
		status.setComplete();
		flash.addFlashAttribute("info", "Producto guardado con exito");
		model.addAttribute("categorias", categoriaService.findAll());
		return "/admin/formProducto";
	}
}

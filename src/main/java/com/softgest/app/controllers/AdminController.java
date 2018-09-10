package com.softgest.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softgest.app.models.entity.Categoria;
import com.softgest.app.models.entity.Producto;
import com.softgest.app.models.entity.Role;
import com.softgest.app.models.entity.Usuario;
import com.softgest.app.models.service.ICategoriaService;
import com.softgest.app.models.service.IProductoService;
import com.softgest.app.models.service.IRoleService;
import com.softgest.app.models.service.IUsuarioService;
import com.softgest.app.util.PageRenderUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {

	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired 
	private IUsuarioService usuarioService;
	
	@Autowired
	private IRoleService roleService;
	
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
	public String listarClientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model, HttpSession session) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Usuario> clientes = usuarioService.findAll(pageRequest);
		PageRenderUtil<Usuario> pageRender = new PageRenderUtil<Usuario>("/admin/listar-clientes", clientes);
		
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		
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
		
		return "/admin/listado-productos";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/modProduct/{id}")
	public String modProduct(@PathVariable("id") Long producto_id, Model model, HttpSession session) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		Producto producto = productoService.findById(producto_id);
		model.addAttribute("producto", producto);
		List<Categoria> categorias = categoriaService.findAll();
		model.addAttribute("categorias", categorias);
		return "/admin/formUpdateProduct";
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping(value="/actualizaProducto")
	public String actualizarProducto(@Valid Producto producto, @RequestParam(value = "file" ,required = false) MultipartFile img, BindingResult result, Model model, HttpSession session, RedirectAttributes flash, SessionStatus status, @RequestParam(value = "create_pro" ,required = false) String createPro) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));		
		producto.setCreatePro(Date.valueOf(createPro));	
		
		if(result.hasErrors()) {
			flash.addFlashAttribute("error", "Infromación del producto no valida.");
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
		}else {
			producto.setImg(productoService.findById(producto.getId()).getImg());
		}
		productoService.saveProducto(producto);
		status.setComplete();
		flash.addFlashAttribute("info", "Producto guardado con exito");
		model.addAttribute("categorias", categoriaService.findAll());
	
		return "/admin/listado-productos";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value="/eliminarProducto/{id}")
	public String eliminarProducto(@PathVariable("id") Long producto_id, Model model, HttpSession session, RedirectAttributes flash) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		model.addAttribute("productos", productoService.findAll());
		Producto producto = productoService.findById(producto_id);
		if(producto != null) {
			producto.setEstado("Inactivo");
			productoService.saveProducto(producto);
			flash.addFlashAttribute("info", "Producto desactivado con exito.");
		}else {
			flash.addFlashAttribute("error", "Error: no se encontro el producto con ese ID.");
		}
		
		return "/admin/listado-productos";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value="/activarProducto/{id}")
	public String activarProducto(@PathVariable("id") Long producto_id, Model model, HttpSession session, RedirectAttributes flash) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		model.addAttribute("productos", productoService.findAll());
		Producto producto = productoService.findById(producto_id);
		if(producto != null) {
			producto.setEstado("Activo");
			productoService.saveProducto(producto);
			flash.addFlashAttribute("info", "Producto desactivado con exito.");
		}else {
			flash.addFlashAttribute("error", "Error: no se encontro el producto con ese ID.");
		}
		
		return "/admin/listado-productos";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/updateUser/{id}")
	public String setAdmin(Model model, HttpSession session, @PathVariable(value = "id") Long usuario_id, RedirectAttributes flash) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		model.addAttribute("clientes", usuarioService.buscarTodos());
		Usuario user = usuarioService.buscarPorId(usuario_id);
		boolean tieneRol = false;
		if(user != null) {
			for(Role rol: user.getRoles()) {
				logger.info("Rol del usuario: " + rol.getAuthority());
				if(rol.getAuthority().equals("ROLE_ADMIN")) {
					tieneRol = true;
				}
			}
			logger.info("ha sido encontrado el Rol: : " + tieneRol);
			if(!tieneRol) {
				model.addAttribute("info", "Rol de administrador establecido.");
				Role rol = new Role();
				rol.setUser(user);
				rol.setAuthority("ROLE_ADMIN");
				roleService.insert(rol);
			}else {
				model.addAttribute("info", "Este usuario ya es administrador.");
			}				
		}
		return "/admin/listado-clientes";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/eliminar/{id}")
	public String deleteUser(Model model, HttpSession session, @PathVariable(value = "id") Long usuario_id, RedirectAttributes flash) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		model.addAttribute("clientes", usuarioService.buscarTodos());
		Usuario user = usuarioService.buscarPorId(usuario_id);
		if(user != null){
			user.setEnabled(false);
			usuarioService.insertar(user);
			model.addAttribute("info", "Usuario deshabilitado!");
		}
		return "/admin/listado-clientes";
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(value = "/activar/{id}")
	public String activeUser(Model model, HttpSession session, @PathVariable(value = "id") Long usuario_id, RedirectAttributes flash) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		model.addAttribute("clientes", usuarioService.buscarTodos());
		Usuario user = usuarioService.buscarPorId(usuario_id);
		if(user != null){
			user.setEnabled(true);
			usuarioService.insertar(user);
			model.addAttribute("info", "Usuario habilitado!");
		}
		return "/admin/listado-clientes";
	}
}

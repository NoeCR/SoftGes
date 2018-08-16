package com.softgest.app.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softgest.app.models.entity.Factura;
import com.softgest.app.models.entity.Usuario;
import com.softgest.app.models.service.IUsuarioService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura") 
public class FacturaController {
	
	@Autowired
	private IUsuarioService usuarioService;

	@GetMapping(value="/form/{usuarioid}")
	public String crearFactura(@PathVariable(value="usuarioid") Long usuarioId, Map<String, Object> model, RedirectAttributes flash, HttpSession session) {
		Usuario usuario = usuarioService.buscarPorId(usuarioId);
		
		// Si no hay cliente se le redirecciona a la pagina de login
		if(usuario == null) {
			flash.addFlashAttribute("warning", "Debe estar registrado para realizar la compra.");
			return "redirect:/login";
		}
		
		Factura factura = new Factura();
		factura.setUsuario(usuario);
		factura.setItems(usuario.getItems());
		
		model.put("factura", factura);
		model.put("usuario", usuario);
		model.put("titulo", "Realizar compra");
		
		return "factura/form";
	}
	
}

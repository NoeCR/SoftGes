package com.softgest.app.controllers;

import javax.servlet.http.HttpSession;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.softgest.app.models.entity.Usuario;
import com.softgest.app.models.entity.UsuarioDetalle;
import com.softgest.app.models.service.IUsuarioDetalleService;


@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	//@Autowired
	//private IUsuarioService usuarioService;
	
	@Autowired
	private IUsuarioDetalleService usuarioDetalleService;
	
	@Secured("ROLE_USER")
	@GetMapping(value = "/verDetalleUsuario/{id}")
	public String verUsuario(@PathVariable(value = "id") Long usuario_id, Model model, HttpSession session) {
		model.addAttribute("usuario", (Usuario) session.getAttribute("usuario"));
		logger.info("ID del usuario: " + usuario_id);
		UsuarioDetalle cliente = usuarioDetalleService.findByUsuario(usuario_id);	
			
		model.addAttribute("cliente", cliente);
		
		return "/usuario/verDetalleUsuario";
	}
}

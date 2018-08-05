package com.softgest.app.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.softgest.app.models.entity.Usuario;
import com.softgest.app.models.service.IUsuarioService;

@Controller
public class LoginController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required= false) String error,  Model model, Principal principal, RedirectAttributes flash,@RequestParam(value="logout", required= false) String logout) {
		
		if(principal != null) {
			flash.addFlashAttribute("info", "Ya hay una sesión iniciada");
			return "redirect:/";
		}
		if(error != null) {
			model.addAttribute("error", "Error login: nombre de usuario o contraseña incorrectas, vuelva a intentarlo.");
		}
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
		return "login";
	}
	@GetMapping(value="/create")
	public String crearRegistro(@ModelAttribute Usuario usuario) {		
		return "formUsuario";
	}
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String guardarRegistro(@ModelAttribute Usuario usuario, BindingResult result, Model model, RedirectAttributes flash) {
		String hashedPassword = passwordEncoder.encode(usuario.getPassword());
		usuario.setPassword(hashedPassword);
		usuarioService.insertar(usuario);
		flash.addFlashAttribute("success", "Usuario registrado con éxito!");
		return "redirect:/";
	}
}

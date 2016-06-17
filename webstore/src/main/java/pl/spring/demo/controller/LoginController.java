package pl.spring.demo.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.spring.demo.constants.ModelConstants;
import pl.spring.demo.constants.ViewNames;

@Controller
public class LoginController {
	
	private static final String INFO_TEXT = "Here You shall display information containing informations about newly created TO";
	private static final String WELCOME = "This is a welcome page";

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(Model model) {
		model.addAttribute("error", "true");
		return ViewNames.LOGIN;

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		model.addAttribute(ModelConstants.GREETING, WELCOME);
		model.addAttribute(ModelConstants.INFO, INFO_TEXT);
		return ViewNames.WELCOME;
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied(Principal user) {
		ModelAndView model = new ModelAndView();
		String userName = user.getName();
		model.addObject("errorMessage"," User: "+userName+" has no access to ressource!");
		// TODO: implement mechanism redirecting to new custom page _403
		// (consider extending informations by custom values)
		return model;

	}
}

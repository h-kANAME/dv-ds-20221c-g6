package ar.edu.davinci.dvds20221cg6.controller.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ar.edu.davinci.dvds20221cg6.controller.TiendaApp;



@Controller
public class HomeController extends TiendaApp {
	
	private final Logger LOGGER = LoggerFactory.getLogger(TiendaApp.class);

	@GetMapping()
	public String viewHomePage(Model model) {
		LOGGER.info("GET - viewHomePage - /index");
		return "index";
	}
}


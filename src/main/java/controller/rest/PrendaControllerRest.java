package controller.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import controller.rest.TiendaAppRest;
import domain.Prenda;
import ma.glasnost.orika.MapperFacade;
import service.PrendaService;

@RestController
public class PrendaControllerRest extends TiendaAppRest {

	private final Logger LOGGER = LoggerFactory.getLogger(PrendaControllerRest.class);
	
	@Autowired
	private PrendaService service;
	
	/**
	 * Listar Prendas
	 */
	@GetMapping(path = "/prendas/all")
	public List<Prenda> getList(){
		LOGGER.info("Listado de todas las prendas");
		return service.list();
	}
	
}


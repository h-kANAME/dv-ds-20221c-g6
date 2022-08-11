package ar.edu.davinci.dvds20221cg6.controller.rest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping(path="tienda/api")
public abstract class TiendaAppRest {
	
	// Configurar la url de la aplicación rest
	// http://localhost:8090/tienda/api


}

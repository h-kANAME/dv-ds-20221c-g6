package ar.edu.davinci.dvds20221cg6.controller.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.davinci.dvds20221cg6.controller.response.NegocioResponse;

import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.service.NegocioService;
import ma.glasnost.orika.MapperFacade;

@RestController
public class NegocioControllerRest extends TiendaAppRest{
	private final Logger LOGGER = LoggerFactory.getLogger(VentaControllerRest.class);
	
	@Autowired
	private NegocioService negocioService;
	
	@Autowired
	private MapperFacade mapper;
	
	@GetMapping(path = "/negocio")
	public ResponseEntity<Page<NegocioResponse>> getList(Pageable pageable) {
		
		
		LOGGER.info("listar todas las ventas paginadas");
		LOGGER.info("Pageable: " + pageable);
		
		Page<NegocioResponse> negocioResponse = null;
		Page<Venta> negocio = null;
		try {
			
			negocio = negocioService.list(pageable);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			negocioResponse = negocio.map(ngo -> mapper.map(ngo, NegocioResponse.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(negocioResponse, HttpStatus.CREATED);
	}
	
	
}

package ar.edu.davinci.dvds20221cg6.controller.rest;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.davinci.dvds20221cg6.controller.request.NegocioInsertRequest;
import ar.edu.davinci.dvds20221cg6.controller.response.NegocioResponse;
import ar.edu.davinci.dvds20221cg6.domain.Negocio;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;
import ar.edu.davinci.dvds20221cg6.service.NegocioService;
import ma.glasnost.orika.MapperFacade;

@CrossOrigin("*")
@RestController
public class NegocioControllerRest extends TiendaAppRest{
	
	private final Logger LOGGER = LoggerFactory.getLogger(NegocioControllerRest.class);
	
	@Autowired
	private MapperFacade mapper;
	
	@Autowired
	private NegocioService service;
	
	@GetMapping(path = "/negocios/all")
	public List<Negocio> getListAll(){
		LOGGER.info("listar todas los negocios");
		return service.list();
	}
	
	public ResponseEntity<Page<NegocioResponse>> getList(Pageable pageable){
		LOGGER.info("listar todas los negocios paginados");
		LOGGER.info("Pageable: " + pageable);
		
		Page<NegocioResponse> negocioResponse = null;
		Page<Negocio> negocios = null;
		
		try {
			negocios = service.list(pageable);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			negocioResponse = negocios.map(negocio -> mapper.map(negocio, NegocioResponse.class));
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(negocioResponse, HttpStatus.OK);
	}
	
	@GetMapping(path = "/negocios/{id}")
	public ResponseEntity<Object> getNegocio(@PathVariable Long id){
		LOGGER.info("lista al negocio solicitado");
		
		NegocioResponse negocioResponse = null;
		Negocio negocio = null;
		
		try {
			negocio = service.findById(id);
			negocio.setVentas(negocio.getVentas());
		}catch(BusinessException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			negocioResponse = mapper.map(negocio, NegocioResponse.class);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(negocioResponse, HttpStatus.OK);
	}
	
	/*@GetMapping(path = "/negocios/{id}/profit/{dia}")
	public ResponseEntity<Object> getNegocio(@PathVariable Long id, @PathVariable String dia){
		LOGGER.info("lista al negocio solicitado");
		
		NegocioResponse negocioResponse = null;
		Negocio negocio = null;
		
		try {
			negocio = service.findById(id);
		}catch(BusinessException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			negocioResponse = mapper.map(negocio, NegocioResponse.class);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(negocioResponse, HttpStatus.OK);
	}*/
	
	@PostMapping(path = "/negocio")
	public ResponseEntity<NegocioResponse> createNegocio(@RequestBody NegocioInsertRequest datosNegocio){
		Negocio negocio = null;
		NegocioResponse negocioResponse = null;
		
		try {
			negocio = mapper.map(datosNegocio, Negocio.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			negocio = service.save(negocio);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		
		try {
			negocioResponse = mapper.map(negocio, NegocioResponse.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(negocioResponse, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/negocios/{id}")
	public ResponseEntity<HttpStatus> deleteNegocio(@PathVariable("id") Long id){
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
}

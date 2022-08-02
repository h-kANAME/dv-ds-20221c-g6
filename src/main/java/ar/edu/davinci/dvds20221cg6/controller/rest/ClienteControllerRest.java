package ar.edu.davinci.dvds20221cg6.controller.rest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.davinci.dvds20221cg6.controller.rest.TiendaAppRest;
import ar.edu.davinci.dvds20221cg6.controller.request.ClienteInsertRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.ClienteUpdateRequest;
import ar.edu.davinci.dvds20221cg6.controller.response.ClienteResponse;
import ar.edu.davinci.dvds20221cg6.domain.Cliente;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;
import ar.edu.davinci.dvds20221cg6.service.ClienteService;
import ma.glasnost.orika.MapperFacade;
@CrossOrigin("*")
@RestController
public class ClienteControllerRest extends TiendaAppRest {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ClienteControllerRest.class);

	@Autowired
	private ClienteService service;
	
	@Autowired
	private MapperFacade mapper;
	
	/**
	 * Listar
	 */
	@GetMapping(path = "/clientes/all")
	public List<Cliente> getListAll() {
		LOGGER.info("listar todas las clientes");

		return service.list();
	}

	/**
	 * Listar paginado
	 */
	@GetMapping(path = "/clientes")
	public ResponseEntity<Page<ClienteResponse>> getList(Pageable pageable) {
		
		LOGGER.info("listar todas las clientes paginadas");
		LOGGER.info("Pageable: " + pageable);
		
		Page<ClienteResponse> clienteResponse = null;
		Page<Cliente> clientes = null;
		try {
			clientes = service.list(pageable);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			clienteResponse = clientes.map(cliente -> mapper.map(cliente, ClienteResponse.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(clienteResponse, HttpStatus.OK);
	}
	
	/**
	 * Buscar cliente por id
	 * @param id identificador del cliente
	 * @return retorna el cliente
	 */
	@GetMapping(path = "/clientes/{id}")
	public ResponseEntity<Object> getCliente(@PathVariable Long id) {
		LOGGER.info("lista al cliente solicitado");

		ClienteResponse clienteResponse = null;
		Cliente cliente = null;
		try {
			
			cliente = service.findById(id);
	
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		try {
			clienteResponse = mapper.map(cliente, ClienteResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(clienteResponse, HttpStatus.OK);
	}
	
	/**
	 * Grabar una nueva cliente
	 * 
	 * @param datosCliente son los datos para una nueva cliente
	 * @return un cliente nueva
	 */
	@PostMapping(path = "/clientes")
	public ResponseEntity<ClienteResponse> createCliente(@RequestBody ClienteInsertRequest datosCliente) {
		Cliente cliente = null;
		ClienteResponse clienteResponse = null;

		// Convertir ClienteInsertRequest en Cliente
		try {
			cliente = mapper.map(datosCliente, Cliente.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		// Grabar el nuevo Cliente
		try {
			cliente = service.save(cliente);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}

		// Convertir Cliente en ClienteResponse
		try {
			clienteResponse = mapper.map(cliente, ClienteResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(clienteResponse, HttpStatus.CREATED);
	}
	
	/**
	 * Modificar los datos de un cliente
	 * 
	 * @param id identificador de una cliente
	 * @param datosCliente datos a modificar de la cliente
	 * @return los datos de una cliente modificada
	 */
	@PutMapping("/clientes/{id}")
	public ResponseEntity<Object> updateCliente(@PathVariable("id") long id,
			@RequestBody ClienteUpdateRequest datosCliente) {

		Cliente clienteModifar = null;
		Cliente clienteNuevo = null;
		ClienteResponse clienteResponse = null;

		// Convertir ClienteInsertRequest en Cliente
		try {
			clienteNuevo = mapper.map(datosCliente, Cliente.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			
			clienteModifar = service.findById(id);

		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		if (Objects.nonNull(clienteModifar)) {
			clienteModifar.setNombre(clienteNuevo.getNombre());
			clienteModifar.setApellido(clienteNuevo.getApellido());
			// Grabar el Cliente Nuevo en Cliente a Modificar
			try {
				clienteModifar = service.update(clienteModifar);
			} catch (BusinessException e) {
				LOGGER.error(e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());

				e.printStackTrace();

				return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
			}
		} else {
			LOGGER.error("Cliente a modificar es null");

			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		// Convertir Cliente en ClienteResponse
		try {
			clienteResponse = mapper.map(clienteModifar, ClienteResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(clienteResponse, HttpStatus.CREATED);
	}
	
	
	/**
	 * Borrado de la  cliente
	 * @param id identificador de una cliente
	 * @return
	 */
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<HttpStatus> deleteCliente(@PathVariable("id") Long id) {
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
}

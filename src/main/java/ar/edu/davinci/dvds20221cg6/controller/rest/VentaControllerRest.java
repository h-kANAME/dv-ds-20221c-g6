package ar.edu.davinci.dvds20221cg6.controller.rest;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.davinci.dvds20221cg6.controller.rest.TiendaAppRest;
import ar.edu.davinci.dvds20221cg6.controller.request.ItemInsertRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.ItemUpdateRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.VentaEfectivoRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.VentaTarjetaRequest;
import ar.edu.davinci.dvds20221cg6.controller.response.VentaResponse;
import ar.edu.davinci.dvds20221cg6.domain.Item;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.domain.VentaEfectivo;
import ar.edu.davinci.dvds20221cg6.domain.VentaTarjeta;
import ar.edu.davinci.dvds20221cg6.service.VentaService;
import ma.glasnost.orika.MapperFacade;

@RestController
public class VentaControllerRest extends TiendaAppRest{
	
	private final Logger LOGGER = LoggerFactory.getLogger(VentaControllerRest.class);

	@Autowired
	private VentaService ventaService;
	
	@Autowired
	private MapperFacade mapper;
	
	/**
	 * Listar
	 */
	@GetMapping(path = "/ventas/all")
	public List<Venta> getListAll() {
		LOGGER.info("listar todas las ventas");

		return ventaService.list();
	}
	

	/**
	 * Listar paginado
	 */
	@GetMapping(path = "/ventas")
	public ResponseEntity<Page<VentaResponse>> getList(Pageable pageable) {
		
		LOGGER.info("listar todas las ventas paginadas");
		LOGGER.info("Pageable: " + pageable);
		
		Page<VentaResponse> ventaResponse = null;
		Page<Venta> ventas = null;
		try {
			ventas = ventaService.list(pageable);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			ventaResponse = ventas.map(venta -> mapper.map(venta, VentaResponse.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
	}
	
	/**
	 * Buscar venta por id
	 * @param ventaId identificador del venta
	 * @return retorna el venta
	 */
	@GetMapping(path = "/ventas/{ventaId}")
	public ResponseEntity<Object> getVenta(@PathVariable Long ventaId) {
		LOGGER.info("lista al venta solicitado");

		VentaResponse ventaResponse = null;
		Venta venta = null;
		try {

			venta = ventaService.findById(ventaId);
	
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		try {
			ventaResponse = mapper.map(venta, VentaResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
	}


	/**
	 * Grabar una nueva venta en Efectivo
	 * 
	 * @param datosVenta son los datos para una nueva venta
	 * @return un venta nueva
	 */
	@PostMapping(path = "/ventas/efectivo")
	public ResponseEntity<VentaResponse> createVenta(@RequestBody VentaEfectivoRequest datosVenta) {
		VentaResponse ventaResponse = null;
		
		VentaEfectivo venta = mapper.map(datosVenta, VentaEfectivo.class);

		return grabarVenta(venta, ventaResponse);
	}


	/**
	 * Grabar una nueva venta de Tarjeta
	 * 
	 * @param datosVenta son los datos para una nueva venta
	 * @return un venta nueva
	 */
	@PostMapping(path = "/ventas/tarjeta")
	public ResponseEntity<VentaResponse> createVenta(@RequestBody VentaTarjetaRequest datosVenta) {
		VentaResponse ventaResponse = null;
		
		VentaTarjeta venta = mapper.map(datosVenta, VentaTarjeta.class);

		return grabarVenta(venta, ventaResponse);
	}

	/**
	 *  Graba una Venta
	 * @param venta
	 * @param ventaResponse
	 * @return
	 */
	private ResponseEntity<VentaResponse> grabarVenta(VentaEfectivo venta, VentaResponse ventaResponse) {
		// Grabar el nuevo Venta
		try {
			venta = ventaService.save(venta);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}

		// Convertir Venta en VentaResponse
		try {
			ventaResponse = mapper.map(venta, VentaResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
	}


	/**
	 *  Graba una Venta
	 * @param venta
	 * @param ventaResponse
	 * @return
	 */
	private ResponseEntity<VentaResponse> grabarVenta(VentaTarjeta venta, VentaResponse ventaResponse) {
		// Grabar el nuevo Venta
		try {
			venta = ventaService.save(venta);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}

		// Convertir Venta en VentaResponse
		try {
			ventaResponse = mapper.map(venta, VentaResponse.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
	}

	
	/**
	 * Grabar un nuevo item a la venta
	 * 
	 * @param ventaId identificador de la venta
	 * @param datosItem son los datos para un nuevo item de venta
	 * @return un venta modificada
	 */
	@PostMapping(path = "/ventas/{ventaId}/items")
	public ResponseEntity<VentaResponse> createItem(@PathVariable("ventaId") long ventaId,
			@RequestBody ItemInsertRequest datosItem) {

		VentaResponse ventaResponse = null;
		
		Item item = null;
		try {
			item = mapper.map(datosItem, Item.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		Venta venta = null;
		try {
			venta = ventaService.addItem(ventaId, item);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		// Convertir Venta en VentaResponse
		try {
			if (venta instanceof VentaEfectivo) {
				ventaResponse = mapper.map((VentaEfectivo) venta, VentaResponse.class);
			} else if (venta instanceof VentaTarjeta) {
				ventaResponse = mapper.map((VentaTarjeta) venta, VentaResponse.class);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
	}
	

	/**
	 * Modifica un item de la venta
	 * 
	 * @param ventaId identificador de la venta
	 * @param itemId identificador del item
	 * @param datosItem son los datos para modificar el item de venta
	 * @return un venta modificada
	 */
	@PutMapping(path = "/ventas/{ventaId}/items/{itemId}")
	public ResponseEntity<VentaResponse> modifyItem(@PathVariable("ventaId") long ventaId,
			@PathVariable("itemId") long itemId,
			@RequestBody ItemUpdateRequest datosItem) {

		VentaResponse ventaResponse = null;
		
		Item item = null;
		try {
			item = mapper.map(datosItem, Item.class);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			//e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}

		Venta venta = null;
		try {
			venta = ventaService.updateItem(ventaId, itemId, item);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			//e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		// Convertir Venta en VentaResponse
		try {
			if (venta instanceof VentaEfectivo) {
				ventaResponse = mapper.map((VentaEfectivo) venta, VentaResponse.class);
			} else if (venta instanceof VentaTarjeta) {
				ventaResponse = mapper.map((VentaTarjeta) venta, VentaResponse.class);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
	}
	

	/**
	 * Borrado del item de la venta
	 * @param ventaId identificador de una venta
	 * @param itemId identificador del item
	 * @return 
	 */
	@DeleteMapping("/ventas/{ventaId}/items/{itemId}")
	public ResponseEntity<VentaResponse> deleteCliente(@PathVariable("ventaId") long ventaId,
			@PathVariable("itemId") long itemId) {
		VentaResponse ventaResponse = null;
		Venta venta = null;
		try {
			venta = ventaService.deleteItem(ventaId, itemId);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		// Convertir Venta en VentaResponse
		try {
			if (venta instanceof VentaEfectivo) {
				ventaResponse = mapper.map((VentaEfectivo) venta, VentaResponse.class);
			} else if (venta instanceof VentaTarjeta) {
				ventaResponse = mapper.map((VentaTarjeta) venta, VentaResponse.class);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}

		return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);

	}

}


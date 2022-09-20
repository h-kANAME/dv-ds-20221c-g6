package ar.edu.davinci.dvds20221cg6.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.dvds20221cg6.domain.Cliente;
import ar.edu.davinci.dvds20221cg6.domain.Item;
import ar.edu.davinci.dvds20221cg6.domain.Negocio;
import ar.edu.davinci.dvds20221cg6.domain.Prenda;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.domain.VentaEfectivo;
import ar.edu.davinci.dvds20221cg6.domain.VentaTarjeta;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;
import ar.edu.davinci.dvds20221cg6.repository.NegocioRepository;
import ar.edu.davinci.dvds20221cg6.repository.VentaEfectivoRepository;
import ar.edu.davinci.dvds20221cg6.repository.VentaRepository;
import ar.edu.davinci.dvds20221cg6.repository.VentaTarjetaRepository;


@Service
public class VentaServiceImpl implements VentaService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(VentaServiceImpl.class);

	
	private final VentaRepository ventaRepository;

	private final VentaEfectivoRepository ventaEfectivoRepository;
	
	private final VentaTarjetaRepository ventaTarjetaRepository;
	
	private final ClienteService clienteService;

	private final PrendaService prendaService;
	
	private final ItemService itemService;
	
	private final NegocioService negocioService;

	
	@Autowired
	public VentaServiceImpl(final VentaRepository ventaRepository,
			final VentaEfectivoRepository ventaEfectivoRepository,
			final VentaTarjetaRepository ventaTarjetaRepository,
			final ClienteService clienteService,
			final PrendaService prendaService,
			final ItemService itemService,
			final NegocioService negocioService) {
		this.ventaRepository = ventaRepository;
		this.ventaEfectivoRepository = ventaEfectivoRepository;
		this.ventaTarjetaRepository = ventaTarjetaRepository;
		this.clienteService = clienteService;
		this.prendaService = prendaService;
		this.itemService = itemService;
		this.negocioService = negocioService;

	}


	@Override
	public VentaEfectivo save(VentaEfectivo venta) throws BusinessException {
		Cliente cliente = null;
		if (venta.getCliente().getId() != null) {
			cliente = getCliente(venta.getCliente().getId()); 
		} else {
			throw new BusinessException("El cliente es obligatorio");
		}

		List<Item> items = new ArrayList<Item>(); 
		if (venta.getItems() != null) {
			items = getItems(venta.getItems());
		}
		
		Negocio negocio = null;
		if(venta.getNegocio().getId() != null) {
			negocio = getNegocio(venta.getNegocio().getId());
		}else {
			throw new BusinessException("El negocio es obligatorio");
		}
		
		venta = VentaEfectivo.builder()
				.cliente(cliente)
				.fecha(Calendar.getInstance().getTime())
				.items(items)
				.negocio(negocio)
				.build();
		
		venta = ventaEfectivoRepository.save(venta);
		
		
		negocioService.addVenta(negocio.getId(), venta);
		
		return venta;

	}


	@Override
	public VentaEfectivo save(VentaEfectivo ventaEfectivo, Item item) throws BusinessException {

		ventaEfectivo.addItem(item);
		
		return ventaEfectivoRepository.save(ventaEfectivo);
	}

	@Override
	public VentaTarjeta save(VentaTarjeta venta) throws BusinessException {
		Cliente cliente = null;
		if (venta.getCliente().getId() != null) {
			cliente = getCliente(venta.getCliente().getId()); 
		} else {
			throw new BusinessException("El cliente es obligatorio");
		}

		List<Item> items = new ArrayList<Item>(); 
		if (venta.getItems() != null) {
			items = getItems(venta.getItems());
		}
		
		Negocio negocio = null;
		if(venta.getNegocio().getId() != null) {
			negocio = getNegocio(venta.getNegocio().getId());
		}else {
			throw new BusinessException("El negocio es obligatorio");
		}
		
		venta = VentaTarjeta.builder()
				.cliente(cliente)
				.fecha(Calendar.getInstance().getTime())
				.items(items)
				.negocio(negocio)
				.cantidadCuotas(venta.getCantidadCuotas())
				.coeficienteTarjeta(new BigDecimal(0.01D))
				.build();
		
		venta = ventaTarjetaRepository.save(venta);

		negocioService.addVenta(negocio.getId(), venta);
		
		return venta;
	}

	@Override
	public VentaTarjeta save(VentaTarjeta ventaTarjeta, Item item) throws BusinessException {
		ventaTarjeta.addItem(item);
		return ventaTarjetaRepository.save(ventaTarjeta);
	}

	@Override
	public void delete(Venta venta) {
		ventaRepository.delete(venta);
	}

	@Override
	public void delete(Long id) {
		ventaRepository.deleteById(id);
	}

	@Override
	public Venta findById(Long id) throws BusinessException {
		LOGGER.debug("Busqueda de una venta por ID");
		
		Optional<Venta> itemOptional = ventaRepository.findById(id);
		if (itemOptional.isPresent()) {
			return itemOptional.get();
		}
		throw new BusinessException("No se encotró la venta por el id: " + id);
	}

	@Override
	public List<Venta> list() {
		LOGGER.debug("Listado de todas las ventas");
		return ventaRepository.findAll();
	}

	@Override
	public Page<Venta> list(Pageable pageable) {
		LOGGER.debug("Listado de todas las ventas por páginas");
		LOGGER.debug("Pageable: offset: " + pageable.getOffset() + ", pageSize: " + pageable.getPageSize() + " and pageNumber: " + pageable.getPageNumber());
		return ventaRepository.findAll(pageable);
	}

	@Override
	public long count() {
		return ventaRepository.count();
	}

	@Override
	public Venta addItem(Long ventaId, Item item) throws BusinessException {
		Venta venta = getVenta(ventaId);
		Prenda prenda = getPrenda(item);
		Item newItem = Item.builder()
				.cantidad(item.getCantidad())
				.prenda(prenda)
				.venta(venta)
				.build();
		if (newItem.getCantidad()<= newItem.getPrenda().getCantidad()) {
			newItem.getPrenda().descontarStock(newItem.getCantidad());
			prendaService.update(newItem.getPrenda());
			newItem = itemService.save(newItem);
			venta.addItem(newItem);
			return venta;
		}else {
			throw new BusinessException("La cantidad ingresada es mayor a la del stock actual: " + ventaId);
		}
	}

	@Override
	public Venta updateItem(Long ventaId, Long itemId, Item item) throws BusinessException {
		Venta venta = getVenta(ventaId);
		Item actualItem = getItem(itemId);
		
		if(item.getCantidad() <= actualItem.getPrenda().getCantidad()) {
			actualItem.getPrenda().agregarStock(actualItem.getCantidad());
			actualItem.setCantidad(item.getCantidad());
			actualItem.getPrenda().descontarStock(item.getCantidad());
			prendaService.update(actualItem.getPrenda());
			actualItem = itemService.update(actualItem);
			return venta;
		}
		throw new BusinessException("La cantidad ingresada es mayor a la del stock actual: " + ventaId);
		
	}

	@Override
	public Venta deleteItem(Long ventaId, Long itemId) throws BusinessException {
		Venta venta = getVenta(ventaId);
		Item actualItem = getItem(itemId);
		actualItem.getPrenda().agregarStock(actualItem.getCantidad());
		prendaService.update(actualItem.getPrenda());
		itemService.delete(itemId);
		venta.getItems().remove(actualItem);
		ventaRepository.save(venta);
		return venta;
	}
	
	
	private Venta getVenta(Long ventaId) throws BusinessException {
		Optional<Venta> ventaOptional = ventaRepository.findById(ventaId);
		if (ventaOptional.isPresent()) {
			return ventaOptional.get();
		} else {
			throw new BusinessException("Venta no encotrado para el id: " + ventaId);
		}
	}
	
	private List<Item> getItems(List<Item> requestItems) throws BusinessException {
		List<Item> items = new ArrayList<Item>();
		for (Item requestItem : requestItems) {
			
			Prenda prenda = getPrenda(requestItem);
			Item item = Item.builder()
					.cantidad(requestItem.getCantidad())
					.prenda(prenda)
					.build();
			items.add(item);
		}
		return items;
	}
	
	private Prenda getPrenda(Item requestItem) throws BusinessException {
		if (requestItem.getPrenda().getId() != null) {
			
			return prendaService.findById(requestItem.getPrenda().getId());
			
		} else {
			throw new BusinessException("La Prenda es obligatoria");
		}
	}


	private Item getItem(Long id) throws BusinessException {
		
		return itemService.findById(id);

	}


	private Cliente getCliente(Long id) throws BusinessException {

		return clienteService.findById(id);
	
	}
	
	private Negocio getNegocio(Long id) throws BusinessException{
		return negocioService.findById(id);
	}

}


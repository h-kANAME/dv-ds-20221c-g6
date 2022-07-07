package ar.edu.davinci.dvds20221cg6.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.dvds20221cg6.domain.Item;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.domain.VentaEfectivo;
import ar.edu.davinci.dvds20221cg6.domain.VentaTarjeta;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;

public interface VentaService {
	
	// Métodos de creación de una venta en Efectivo
	VentaEfectivo save(VentaEfectivo venta) throws BusinessException;
	VentaEfectivo save(VentaEfectivo venta, Item item) throws BusinessException;

	
	// Métodos de creación de una venta en Tarjeta
	VentaTarjeta save(VentaTarjeta venta) throws BusinessException;
	VentaTarjeta save(VentaTarjeta venta, Item item) throws BusinessException;
	
	
	void delete(Venta venta);
	void delete(Long id);

	// Método de búsqueda.
	Venta findById(Long id) throws BusinessException;
	
	
	// Método de listado.
	List<Venta> list();
	Page<Venta> list(Pageable pageable);
	
	// Método para contar cantidad de datos.
	long count();
	
	// Alta de un item de venta
	public Venta addItem(Long ventaId, Item item) throws BusinessException;

	// Modificación de un item de venta
	public Venta updateItem(Long ventaId, Long itemId, Item item) throws BusinessException;
	
	// Baja de un item de venta
	public Venta deleteItem(Long ventaId, Long itemId) throws BusinessException;

}

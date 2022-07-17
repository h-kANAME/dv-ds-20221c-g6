package ar.edu.davinci.dvds20221cg6.service;

import ar.edu.davinci.dvds20221cg6.domain.Negocio;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;

public interface NegocioService {
	
	Negocio save(Negocio negocio)throws BusinessException;
	
	void delete(Negocio negocio);
	void delete(Long id);
	
	Negocio findById(Long id) throws BusinessException;
	
	// Método para contar cantidad de datos.
	long count();
	
	// Alta de una venta en Negocio
	public Negocio addVenta(Long negocioId, Venta venta) throws BusinessException;

	// Modificación de una venta en Negocio
	public Negocio updateVenta(Long negocioId, Long ventaId, Venta venta) throws BusinessException;
	
	// Baja de un venta de venta en Negocio
	public Negocio deleteVenta(Long negocioId, Long ventaId) throws BusinessException;
}

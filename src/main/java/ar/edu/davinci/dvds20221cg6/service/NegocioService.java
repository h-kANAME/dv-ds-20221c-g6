package ar.edu.davinci.dvds20221cg6.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.dvds20221cg6.domain.Negocio;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.domain.VentaEfectivo;
import ar.edu.davinci.dvds20221cg6.domain.VentaTarjeta;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;

public interface NegocioService {
	
	Negocio save(Negocio negocio)throws BusinessException;
	
	Negocio findById(Long id)throws BusinessException;
	
	Negocio update(Negocio negocio) throws BusinessException;
	
	Negocio addVenta(Long negocioId, Venta venta) throws BusinessException;
	
	Negocio getNegocio(Long id) throws BusinessException;
	
	Page<Negocio> list(Pageable pageable);
	
	List<Negocio> list();
	
	void delete(Long id)throws BusinessException;
	
	public List<Venta> getVentas(List<Venta> requestVentas) throws BusinessException;
	
	public Boolean existVenta(Long negocioId, Long ventaId) throws BusinessException;
}

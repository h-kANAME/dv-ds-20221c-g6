package ar.edu.davinci.dvds20221cg6.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.dvds20221cg6.domain.EstadoPrenda;
import ar.edu.davinci.dvds20221cg6.domain.Prenda;
import ar.edu.davinci.dvds20221cg6.domain.TipoPrenda;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;

public interface PrendaService{
	
	// Métodos de negocio sobre la entida Prenda

	// Métodos de creación, modificación y eliminación de una prenda
	Prenda save(Prenda prenda) throws BusinessException;
	Prenda update(Prenda prenda) throws BusinessException;
	void delete(Prenda prenda);
	void delete(Long id);

	// Métodos de búsqueda
	Prenda findById(Long id) throws BusinessException;
	
	// Métodos de listado
	List<Prenda> list();
	Page<Prenda> list(Pageable pegeable);
	
	// Método de contar la cantidad de registros
	long count();
	
	// Devuelve los tipos de prendas
	List<TipoPrenda> getTipoPrendas();
	
	// Devuelve los estados de prendas
	List<EstadoPrenda> getEstadoPrendas();
	
}

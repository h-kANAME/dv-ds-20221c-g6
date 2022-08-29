package ar.edu.davinci.dvds20221cg6.service;

import ar.edu.davinci.dvds20221cg6.domain.Stock;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;

public interface StockService {
	
	// Métodos de ABM stock de una prenda
	Stock save(Stock stock) throws BusinessException;
	Stock update(Stock stock) throws BusinessException;
	
		
	// Método de contar la cantidad de stock
	Integer getCantidad();
	Stock findById(Long stockId) throws BusinessException;

}
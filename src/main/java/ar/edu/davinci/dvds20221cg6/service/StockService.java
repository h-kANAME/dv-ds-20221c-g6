package ar.edu.davinci.dvds20221cg6.service;

import ar.edu.davinci.dvds20221cg6.domain.Stock;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;

public interface StockService {
	Stock save(Stock stock)throws BusinessException;
	
	Stock update(Stock stock)throws BusinessException;
}

package ar.edu.davinci.dvds20221cg6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.davinci.dvds20221cg6.domain.Stock;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;
import ar.edu.davinci.dvds20221cg6.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService{
	
	private StockRepository repository;
	
	@Autowired
	public StockServiceImpl(final StockRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Stock save(Stock stock) throws BusinessException {
		if(stock.getId() == null) {
			stock.setCantidad(0);
			return repository.save(stock);
		}
		throw new BusinessException("No se puede crear el stock con un id específico.");
	}

	@Override
	public Stock update(Stock stock) throws BusinessException {
		if(stock.getId() != null) {
			return repository.save(stock);
		}
		throw new BusinessException("No se puede modificar un stock que aún no fue creada.");
	}

}

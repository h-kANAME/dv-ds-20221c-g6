package ar.edu.davinci.dvds20221cg6.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.davinci.dvds20221cg6.domain.Stock;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;
import ar.edu.davinci.dvds20221cg6.repository.StockRepository;

@Service
public class StockServiceImpl implements StockService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

	private StockRepository repository;
	
	private Stock stock;
	
	@Autowired
	public StockServiceImpl(final StockRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Stock save(Stock stock) throws BusinessException {
		LOGGER.debug("Grabamos la prenda: " + stock.toString());
		if (stock.getId() == null) {
			return repository.save(stock);
		}
		throw new BusinessException("No se puede crear stock en la prenda con un id específico.");
	}

	@Override
	public Stock update(Stock stock) throws BusinessException {
		LOGGER.debug("Modificamos la prenda: " + stock.toString());
		if (stock.getId() != null) {
			return repository.save(stock);
		}
		throw new BusinessException("No se puede modificar stock en una prenda que aún no fue creada.");
	}

	@Override
	public Integer getCantidad() {
		return stock.getCantidad() ;
	}

	@Override
	public Stock findById(Long stockId) throws BusinessException {
		LOGGER.debug("Buscamos a la stock por id: " + stockId);
		Optional<Stock> stockOptional = repository.findById(stockId);
		if (stockOptional.isPresent()) {
			return stockOptional.get();
		}
		throw new BusinessException("No se encontró stock con el id: " + stockId);
	}

}

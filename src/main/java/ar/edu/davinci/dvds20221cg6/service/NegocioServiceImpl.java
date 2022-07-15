package ar.edu.davinci.dvds20221cg6.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.repository.VentaRepository;

public class NegocioServiceImpl implements NegocioService{
	private final Logger LOGGER = LoggerFactory.getLogger(VentaServiceImpl.class);
	
	private final VentaRepository ventaRepository;
	@Autowired
	public NegocioServiceImpl(final VentaRepository ventaRepository) {
		this.ventaRepository = ventaRepository;
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

}

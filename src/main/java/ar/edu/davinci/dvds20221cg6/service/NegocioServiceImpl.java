package ar.edu.davinci.dvds20221cg6.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.dvds20221cg6.domain.Negocio;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;
import ar.edu.davinci.dvds20221cg6.repository.NegocioRepository;
import ar.edu.davinci.dvds20221cg6.repository.VentaRepository;

@Service
public class NegocioServiceImpl implements NegocioService{
	private final Logger LOGGER = LoggerFactory.getLogger(VentaServiceImpl.class);
	
	private final NegocioRepository negocioRepository;
	private final VentaRepository ventaRepository;
	
	@Autowired
	public NegocioServiceImpl(final VentaRepository ventaRepository, final NegocioRepository negocioRepository) {
		this.ventaRepository = ventaRepository;
		this.negocioRepository = negocioRepository;
	}
	
	
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
	public Negocio addVenta(Long negocioId, Venta venta) throws BusinessException {
		Negocio negocio = getNegocio(negocioId);
		negocio.getVentas().add(venta);
		return negocio;
	}


	private Negocio getNegocio(Long negocioId)throws BusinessException {
		Optional<Negocio> negocioOptional = negocioRepository.findById(negocioId);
		if (negocioOptional.isPresent()) {
			return negocioOptional.get();
		} else {
			throw new BusinessException("Negocio no encontrado para el id: " + negocioId);
		}
	
	}

}

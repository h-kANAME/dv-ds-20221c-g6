package ar.edu.davinci.dvds20221cg6.service;

import ar.edu.davinci.dvds20221cg6.domain.Negocio;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;

public class NegocioServiceImpl implements NegocioService {

	@Override
	public Negocio save(Negocio negocio) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Negocio negocio) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Negocio findById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Negocio addVenta(Long negocioId, Venta venta) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Negocio updateVenta(Long negocioId, Long ventaId, Venta venta) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Negocio deleteVenta(Long negocioId, Long ventaId) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}

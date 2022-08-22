package ar.edu.davinci.dvds20221cg6.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.dvds20221cg6.domain.Cliente;
import ar.edu.davinci.dvds20221cg6.domain.Negocio;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.domain.VentaEfectivo;
import ar.edu.davinci.dvds20221cg6.domain.VentaTarjeta;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;
import ar.edu.davinci.dvds20221cg6.repository.NegocioRepository;

@Service
public class NegocioServiceImpl implements NegocioService{
	private final Logger LOGGER = LoggerFactory.getLogger(ClienteServiceImpl.class);
	private final NegocioRepository negocioRepository;
	private final ClienteService clienteService;
	
	@Autowired
	public NegocioServiceImpl(final NegocioRepository negocioRepository, 
								final ClienteService clienteService) {
		this.negocioRepository = negocioRepository;
		this.clienteService = clienteService;
	}
	
	@Override
	public Negocio save(Negocio negocio) throws BusinessException {
		// TODO Auto-generated method stub
		if(negocio.getId() == null) {
			return negocioRepository.save(negocio);
		}
		
		throw new BusinessException("No se puede crear el negocio con un id específico.");
	}

	@Override
	public Negocio findById(Long id) throws BusinessException {
		Optional<Negocio> negocioOptional = negocioRepository.findById(id);
		if(negocioOptional.isPresent()) {
			return negocioOptional.get();
		}
		
		throw new BusinessException("No se encontró el negocio por el id: " + id);
	}

	@Override
	public Page<Negocio> list(Pageable pageable) {
		// TODO Auto-generated method stub
		return negocioRepository.findAll(pageable);
	}
	
	@Override
	public List<Negocio> list(){
		return negocioRepository.findAll();
	}

	@Override
	public List<Venta> getVentas(List<Venta> requestVentas) throws BusinessException {
		// TODO Auto-generated method stub
		List<Venta> ventas = new ArrayList<Venta>();
		
		for(Venta requestVenta : requestVentas) {
			if(requestVenta instanceof VentaEfectivo) {
				
				ventas.add(getVentaEfectivo((VentaEfectivo) requestVenta));
						
			}else if(requestVenta instanceof VentaTarjeta) {
				
				ventas.add(getVentaTarjeta((VentaTarjeta) requestVenta));
			}
		}
		
		return ventas;
	}
	
	@Override
	public void delete(Long id) throws BusinessException {
		LOGGER.debug("Borrando el negocio con el id: " + id);
		negocioRepository.deleteById(id);
	}
	
	@Override
	public Boolean existVenta(Long negocioId, Long ventaId) throws BusinessException {
		Negocio negocio = findById(negocioId);
		Boolean existVenta = negocio.getVentas().stream().anyMatch(v -> v.getId() == ventaId);
		return existVenta;
	}
	
	private VentaTarjeta getVentaTarjeta(VentaTarjeta requestVentaTarjeta) {
		VentaTarjeta ventaTarjeta = VentaTarjeta.builder()
				.cliente(requestVentaTarjeta.getCliente())
				.fecha(requestVentaTarjeta.getFecha())
				.items(requestVentaTarjeta.getItems())
				.cantidadCuotas(requestVentaTarjeta.getCantidadCuotas())
				.coeficienteTarjeta(requestVentaTarjeta.getCoeficienteTarjeta())
				.build();
		return ventaTarjeta;
		
	}
	
	private VentaEfectivo getVentaEfectivo(VentaEfectivo requestVentaEfectivo) throws BusinessException {
		Cliente cliente = getCliente(requestVentaEfectivo.getCliente().getId());
		VentaEfectivo ventaEfectivo = VentaEfectivo.builder()
				.cliente(cliente)
				.fecha(requestVentaEfectivo.getFecha())
				.items(requestVentaEfectivo.getItems())
				.build();
		return ventaEfectivo;
	}
	
	private Cliente getCliente(Long id) throws BusinessException{
		return clienteService.findById(id);
	}

	@Override
	public Negocio addVenta(Long negocioId, Venta venta) throws BusinessException {
		Negocio negocio = negocioRepository.getById(negocioId);
		negocio.addVenta(venta);
		return negocioRepository.save(negocio);
	}

}

package ar.edu.davinci.dvds20221cg6.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		List<Venta> ventas = new ArrayList<Venta>();
		
		if(negocio.getVentas() != null) {
			ventas = getVentas(negocio.getVentas());
		}
		
		negocio = Negocio.builder()
				.ventas(ventas)
				.build();
		
		return negocioRepository.save(negocio);
	}

	@Override
	public Negocio findById(Long id) throws BusinessException {
		Optional<Negocio> negocioOptional = negocioRepository.findById(id);
		if(negocioOptional.isPresent()) {
			return negocioOptional.get();
		}
		
		throw new BusinessException("No se encontró el negocio por el id: " + id);
	}
	
	private Negocio getNegocio(Long negocioId)throws BusinessException {
		Optional<Negocio> negocioOptional = negocioRepository.findById(negocioId);
		if(negocioOptional.isPresent()) {
			return negocioOptional.get();
		}else {
			throw new BusinessException("Negocio no encontrado");
		}
	}

	@Override
	public Page<Negocio> list(Pageable pageable) {
		// TODO Auto-generated method stub
		return negocioRepository.findAll(pageable);
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

}

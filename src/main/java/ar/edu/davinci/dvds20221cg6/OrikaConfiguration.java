package ar.edu.davinci.dvds20221cg6;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.davinci.dvds20221cg6.controller.request.ItemInsertRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.ItemUpdateRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.NegocioDateRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.NegocioInsertRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.NegocioUpdateRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.VentaEfectivoRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.VentaTarjetaRequest;
import ar.edu.davinci.dvds20221cg6.controller.response.ItemResponse;
import ar.edu.davinci.dvds20221cg6.controller.response.NegocioResponse;
import ar.edu.davinci.dvds20221cg6.controller.response.VentaEfectivoResponse;
import ar.edu.davinci.dvds20221cg6.controller.response.VentaResponse;
import ar.edu.davinci.dvds20221cg6.controller.response.VentaTarjetaResponse;
import ar.edu.davinci.dvds20221cg6.controller.view.request.VentaEfectivoCreateRequest;
import ar.edu.davinci.dvds20221cg6.controller.view.request.VentaItemCreateRequest;
import ar.edu.davinci.dvds20221cg6.controller.view.request.VentaTarjetaCreateRequest;
import ar.edu.davinci.dvds20221cg6.domain.Item;
import ar.edu.davinci.dvds20221cg6.domain.Negocio;
import ar.edu.davinci.dvds20221cg6.domain.VentaEfectivo;
import ar.edu.davinci.dvds20221cg6.domain.VentaTarjeta;
import ar.edu.davinci.dvds20221cg6.controller.request.ClienteInsertRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.ClienteUpdateRequest;
import ar.edu.davinci.dvds20221cg6.controller.response.ClienteResponse;
import ar.edu.davinci.dvds20221cg6.domain.Cliente;
import ar.edu.davinci.dvds20221cg6.domain.EstadoPrenda;
import ar.edu.davinci.dvds20221cg6.domain.EstadoPrendaStrategy;
import ar.edu.davinci.dvds20221cg6.controller.request.PrendaInsertRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.PrendaUpdateRequest;
import ar.edu.davinci.dvds20221cg6.controller.response.PrendaResponse;
import ar.edu.davinci.dvds20221cg6.domain.Prenda;
import ar.edu.davinci.dvds20221cg6.domain.Stock;
import ar.edu.davinci.dvds20221cg6.domain.StrategyFactory;
import ar.edu.davinci.dvds20221cg6.domain.TipoPrenda;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Configuration
public class OrikaConfiguration {
	
	
	private final Logger LOGGER = LoggerFactory.getLogger(OrikaConfiguration.class);
	
	private final ObjectMapper objectMapper;
	
	private final StrategyFactory factory;
	
	public OrikaConfiguration() {
		objectMapper = new ObjectMapper();
		factory = new StrategyFactory();
	}
	
	@Bean
	public MapperFacade mapper() {
		// Instanciando un mapper factory por default
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
	
		// PRENDA
		mapperFactory.classMap(Prenda.class, PrendaResponse.class)
		.customize(new CustomMapper<Prenda, PrendaResponse>() {
			public void mapAtoB(final Prenda prenda, final PrendaResponse prendaResponse, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for Prenda --> PrendaResponse #### ");
				prendaResponse.setId(prenda.getId());
				prendaResponse.setDescripcion(prenda.getDescripcion());
				prendaResponse.setTipo(prenda.getTipo().getDescripcion());
				prendaResponse.setEstado(prenda.getEstado().getDescripcion());
				prendaResponse.setPrecioBase(prenda.getPrecioBase());
				prendaResponse.setPrecioFinal(prenda.getPrecioFinal());
				prendaResponse.setCantidad(prenda.getCantidad());
			}
		}).register();
		
		mapperFactory.classMap(PrendaInsertRequest.class, Prenda.class)
		.customize(new CustomMapper<PrendaInsertRequest, Prenda>() {
			public void mapAtoB(final PrendaInsertRequest prendaInsertRequest, final Prenda prenda, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for prendaInsertRequest --> Prenda #### ");
				
				prenda.setDescripcion(prendaInsertRequest.getDescripcion());
				prenda.setPrecioBase(prendaInsertRequest.getPrecioBase());
				
				TipoPrenda tipoPrenda = TipoPrenda.valueOf(prendaInsertRequest.getTipo().toUpperCase());
				prenda.setTipo(tipoPrenda);
				
				EstadoPrenda estadoPrenda = EstadoPrenda.valueOf(prendaInsertRequest.getEstado().toUpperCase());
				prenda.setEstado(estadoPrenda);
				
				EstadoPrendaStrategy strategy = factory.getStrategy(estadoPrenda);
				prenda.setStateStrategy(strategy);
				
				Stock stock=Stock.builder().cantidad(prendaInsertRequest.getCantidad()).build();
				prenda.setStock(stock);
				
				prenda.setPrecioFinal(strategy.obtenerPrecioVenta(prenda.getPrecioBase()));
			}
		}).register();
		
		mapperFactory.classMap(PrendaUpdateRequest.class, Prenda.class)
		.customize(new CustomMapper<PrendaUpdateRequest, Prenda>() {
			public void mapAtoB(final PrendaUpdateRequest prendaUpdateRequest, final Prenda prenda, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for prendaUpdateRequest --> Prenda #### ");
					
				prenda.setDescripcion(prendaUpdateRequest.getDescripcion());
				prenda.setPrecioBase(prendaUpdateRequest.getPrecioBase());
				
				TipoPrenda tipoPrenda = TipoPrenda.valueOf(prendaUpdateRequest.getTipo().toUpperCase());
				prenda.setTipo(tipoPrenda);
				
				EstadoPrenda estadoPrenda = EstadoPrenda.valueOf(prendaUpdateRequest.getEstado().toUpperCase());
				prenda.setEstado(estadoPrenda);
				
				EstadoPrendaStrategy strategy = factory.getStrategy(estadoPrenda);
				prenda.setStateStrategy(strategy);
				
				prenda.setPrecioFinal(strategy.obtenerPrecioVenta(prenda.getPrecioBase()));
				
			}
		}).register();
		
		//NEGOCIO
		
		mapperFactory.classMap(Negocio.class, NegocioResponse.class)
		.customize(new CustomMapper<Negocio, NegocioResponse>(){
			public void mapAtoB(final Negocio negocio, final NegocioResponse negocioResponse, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for Negocio --> NegocioResponse #### ");
							
				negocioResponse.setId(negocio.getId());
				negocioResponse.setName(negocio.getName());
				negocioResponse.setGananciaTotal(negocio.calcularGananciaTotal());
				negocioResponse.setVentas(new ArrayList<VentaResponse>());
				
				for(Venta venta: negocio.getVentas()) {
					List<ItemResponse> items = new ArrayList<ItemResponse>();
					
					ClienteResponse cliente = ClienteResponse.builder()
							.id(venta.getCliente().getId())
							.nombre(venta.getCliente().getNombre())
							.apellido(venta.getCliente().getApellido())
							.build();
					
					for (Item item : venta.getItems()) {
						PrendaResponse prendaResponse = PrendaResponse.builder()
								.id(item.getPrenda().getId())
								.descripcion(item.getPrenda().getDescripcion())
								.tipo(item.getPrenda().getTipo().getDescripcion())
								.estado(item.getPrenda().getEstado().getDescripcion())
								.precioBase(item.getPrenda().getPrecioBase())
								.precioFinal(item.getPrenda().getPrecioFinal())
								.cantidad(item.getPrenda().getCantidad())
								.build();
						ItemResponse itemResponse = ItemResponse.builder()
						.id(item.getId())
						.cantidad(item.getCantidad())
						.prenda(prendaResponse)
						.importe(item.importe())
						.build();
						
						
						items.add(itemResponse);
					}
					
					
					
					if(venta instanceof VentaTarjeta) {
						
						VentaTarjetaResponse tarjetaResponse = new VentaTarjetaResponse();
						VentaTarjeta ventaTarjeta = (VentaTarjeta) venta;
						tarjetaResponse.setId(ventaTarjeta.getId());
						tarjetaResponse.setFecha(ventaTarjeta.getFecha().toString());
						tarjetaResponse.setImporteFinal(ventaTarjeta.importeFinal());
						tarjetaResponse.setCantidadCuotas(ventaTarjeta.getCantidadCuotas());
						tarjetaResponse.setCoeficienteTarjeta(ventaTarjeta.getCoeficienteTarjeta());
						tarjetaResponse.setIdNegocio(ventaTarjeta.getNegocio().getId());
						tarjetaResponse.setCliente(cliente);
						tarjetaResponse.setItems(items);
						negocioResponse.getVentas().add(tarjetaResponse);
						
					}else if(venta instanceof VentaEfectivo) {
						
						VentaEfectivoResponse efectivoResponse = new VentaEfectivoResponse();
						VentaEfectivo ventaEfectivo = (VentaEfectivo) venta;
						efectivoResponse.setId(ventaEfectivo.getId());
						efectivoResponse.setFecha(ventaEfectivo.getFecha().toString());
						efectivoResponse.setImporteFinal(ventaEfectivo.importeFinal());
						efectivoResponse.setIdNegocio(ventaEfectivo.getNegocio().getId());
						efectivoResponse.setCliente(cliente);
						efectivoResponse.setItems(items);
						negocioResponse.getVentas().add(efectivoResponse);
						
					}
			
				}
				
			}
		}).register();
		
		mapperFactory.classMap(NegocioInsertRequest.class, Negocio.class)
		.customize(new CustomMapper<NegocioInsertRequest, Negocio>() {
			public void mapAtoB(final NegocioInsertRequest negocioInsertRequest, final Negocio negocio, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for negocioInsertRequest --> Negocio #### ");
				negocio.setName(negocioInsertRequest.getName());
			}
		}).register();
		
		mapperFactory.classMap(NegocioUpdateRequest.class, Negocio.class)
		.customize(new CustomMapper<NegocioUpdateRequest, Negocio>() {
			public void mapAtoB(final NegocioUpdateRequest negocioUpdateRequest, final Negocio negocio, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for negocioInsertRequest --> Negocio #### ");
				negocio.setName(negocioUpdateRequest.getName());
			}
		}).register();
		
		// CLIENTE

		mapperFactory.classMap(Cliente.class, ClienteInsertRequest.class).byDefault().register();
		mapperFactory.classMap(Cliente.class, ClienteUpdateRequest.class).byDefault().register();
		mapperFactory.classMap(Cliente.class, ClienteResponse.class).byDefault().register();

		// ITEM

		mapperFactory.classMap(ItemInsertRequest.class, Item.class)
		.customize(new CustomMapper<ItemInsertRequest, Item>() {
			public void mapAtoB(final ItemInsertRequest itemInsertRequest, final Item item, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for itemInsertRequest --> Item #### ");
				Prenda prenda = Prenda.builder()
						.id(itemInsertRequest.getPrendaId())
						.build();
				Stock stock = Stock.builder()
						.id(prenda.getStock().getId())
						.build();
				item.setPrenda(prenda);
				item.getPrenda().setStock(stock);
				item.setCantidad(itemInsertRequest.getCantidad());
			}
		}).register();

		mapperFactory.classMap(ItemUpdateRequest.class, Item.class)
		.customize(new CustomMapper<ItemUpdateRequest, Item>() {
			public void mapAtoB(final ItemUpdateRequest itemUpdateRequest, final Item item, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for itemUpdateRequest --> Item #### ");
				item.setCantidad(itemUpdateRequest.getCantidad());
				//item.getPrenda().setStock(item);
			}
		}).register();

		mapperFactory.classMap(Item.class, ItemResponse.class)
		.customize(new CustomMapper<Item, ItemResponse>() {
			public void mapAtoB(final Item item, final ItemResponse itemResponse, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for Item --> ItemResponse #### ");
				PrendaResponse prendaResponse = PrendaResponse.builder()
						.id(item.getPrenda().getId())
						.descripcion(item.getPrenda().getDescripcion())
						.estado(item.getPrenda().getEstado().getDescripcion())
						.tipo(item.getPrenda().getTipo().getDescripcion())
						.precioBase(item.getPrenda().getPrecioBase())
						.cantidad(item.getPrenda().getCantidad())
						.build();
				itemResponse.setId(item.getId());
				itemResponse.setCantidad(item.getCantidad());
				itemResponse.setPrenda(prendaResponse);
				itemResponse.setImporte(item.importe());
				
			}
		}).register();		
		
		// VENTA EFECTIVO
		
		mapperFactory.classMap(VentaEfectivoRequest.class, VentaEfectivo.class)
		.customize(new CustomMapper<VentaEfectivoRequest, VentaEfectivo>() {
			public void mapAtoB(final VentaEfectivoRequest ventaEfectivoRequest, final VentaEfectivo venta, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for VentaEfectivoRequest --> VentaEfectivo #### ");
				Cliente cliente = Cliente.builder()
						.id(ventaEfectivoRequest.getClienteId())
						.build();
				Negocio negocio = Negocio.builder()
						.id(ventaEfectivoRequest.getNegocioId())
						.build();
				venta.setCliente(cliente);
				venta.setNegocio(negocio);
			}
		}).register();
		
		mapperFactory.classMap(VentaEfectivo.class, VentaEfectivoResponse.class)
		.customize(new CustomMapper<VentaEfectivo, VentaEfectivoResponse>() {
			public void mapAtoB(final VentaEfectivo venta, final VentaEfectivoResponse ventaResponse, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for VentaEfectivo --> VentaEfectivoResponse #### ");
				
				ClienteResponse cliente = ClienteResponse.builder()
						.id(venta.getCliente().getId())
						.nombre(venta.getCliente().getNombre())
						.apellido(venta.getCliente().getApellido())
						.build();
				
				NegocioResponse negocio = NegocioResponse.builder()
						.id(venta.getNegocio().getId())
						.build();
				
				ventaResponse.setId(venta.getId());
				ventaResponse.setCliente(cliente);
				ventaResponse.setIdNegocio(negocio.getId());

				DateFormat formatearFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA);
				String fechaStr = formatearFecha.format(venta.getFecha());
				
				ventaResponse.setFecha(fechaStr);
				ventaResponse.setImporteFinal(venta.importeFinal());
				
				ventaResponse.setItems(new ArrayList<ItemResponse>());
				for (Item item : venta.getItems()) {
							
					PrendaResponse prendaResponse = PrendaResponse.builder()
							.id(item.getPrenda().getId())
							.descripcion(item.getPrenda().getDescripcion())
							.estado(item.getPrenda().getEstado().getDescripcion())
							.tipo(item.getPrenda().getTipo().getDescripcion())
							.estado(item.getPrenda().getEstado().getDescripcion())
							.precioBase(item.getPrenda().getPrecioBase())
							.precioFinal(item.getPrenda().getPrecioFinal())
							.cantidad(item.getPrenda().getCantidad())

							.build();
					ItemResponse itemResponse = ItemResponse.builder()
					.id(item.getId())
					.cantidad(item.getCantidad())
					.prenda(prendaResponse)
					.importe(item.importe())
					.build();

					ventaResponse.getItems().add(itemResponse);
				}
			}
		}).register();
		
		
		// VENTA TARJETA
		
		mapperFactory.classMap(VentaTarjetaRequest.class, VentaTarjeta.class)
		.customize(new CustomMapper<VentaTarjetaRequest, VentaTarjeta>() {
			public void mapAtoB(final VentaTarjetaRequest ventaTarjetaRequest, final VentaTarjeta venta, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for VentaTarjetaRequest --> VentaTarjeta #### ");
				Cliente cliente = Cliente.builder()
						.id(ventaTarjetaRequest.getClienteId())
						.build();
				Negocio negocio = Negocio.builder()
						.id(ventaTarjetaRequest.getNegocioId())
						.build();
				venta.setCliente(cliente);
				venta.setNegocio(negocio);
				venta.setCantidadCuotas(ventaTarjetaRequest.getCantidadCuotas());
			}
		}).register();
		//mapperFactory.classMap(Cliente.class, ClienteUpdateRequest.class).byDefault().register();
		mapperFactory.classMap(VentaTarjeta.class, VentaTarjetaResponse.class)
		.customize(new CustomMapper<VentaTarjeta, VentaTarjetaResponse>() {
			public void mapAtoB(final VentaTarjeta venta, final VentaTarjetaResponse ventaResponse, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for VentaTarjeta --> VentaTarjetaResponse #### ");
				
				ClienteResponse cliente = ClienteResponse.builder()
						.id(venta.getCliente().getId())
						.nombre(venta.getCliente().getNombre())
						.apellido(venta.getCliente().getApellido())
						.build();
				NegocioResponse negocio = NegocioResponse.builder()
						.id(venta.getNegocio().getId())
						.build();
				
				ventaResponse.setId(venta.getId());
				ventaResponse.setCliente(cliente);
				ventaResponse.setIdNegocio(negocio.getId());

				DateFormat formatearFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA);
				String fechaStr = formatearFecha.format(venta.getFecha());
				
				ventaResponse.setFecha(fechaStr);
				ventaResponse.setImporteFinal(venta.importeFinal());
				
				ventaResponse.setItems(new ArrayList<ItemResponse>());
				for (Item item : venta.getItems()) {
					PrendaResponse prendaResponse = PrendaResponse.builder()
							.id(item.getPrenda().getId())
							.descripcion(item.getPrenda().getDescripcion())
							.tipo(item.getPrenda().getTipo().getDescripcion())
							.estado(item.getPrenda().getEstado().getDescripcion())
							.precioBase(item.getPrenda().getPrecioBase())
							.precioFinal(item.getPrenda().getPrecioFinal())
							.cantidad(item.getPrenda().getCantidad())							
							.build();
					ItemResponse itemResponse = ItemResponse.builder()
					.id(item.getId())
					.cantidad(item.getCantidad())
					.prenda(prendaResponse)
					.importe(item.importe())
					.build();

					ventaResponse.getItems().add(itemResponse);
				}
				
				ventaResponse.setCantidadCuotas(venta.getCantidadCuotas());
				ventaResponse.setCoeficienteTarjeta(venta.getCoeficienteTarjeta());
			}
		}).register();
		
		// VENTA EFECTIVO VIEW
		
        mapperFactory.classMap(VentaEfectivoCreateRequest.class, VentaEfectivo.class)
        .customize(new CustomMapper<VentaEfectivoCreateRequest, VentaEfectivo>() {
            public void mapAtoB(final VentaEfectivoCreateRequest ventaEfectivoRequest, final VentaEfectivo venta, final MappingContext context) {
                LOGGER.info(" #### Custom mapping for VentaEfectivoCreateRequest --> VentaEfectivo #### ");
                Cliente cliente = Cliente.builder()
                        .id(ventaEfectivoRequest.getClienteId())
                        .build();
                Negocio negocio = Negocio.builder()
                		.id(ventaEfectivoRequest.getNegocioId())
                		.build();
                venta.setCliente(cliente);
                venta.setNegocio(negocio);
        		DateFormat formatearFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA);
                Date fecha;
				try {
					fecha = formatearFecha.parse(ventaEfectivoRequest.getFecha());
	        		venta.setFecha(fecha);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }).register();
        
        
        // VENTA TARJETA VIEW
        
        mapperFactory.classMap(VentaTarjetaCreateRequest.class, VentaTarjeta.class)
        .customize(new CustomMapper<VentaTarjetaCreateRequest, VentaTarjeta>() {
            public void mapAtoB(final VentaTarjetaCreateRequest ventaTarjetaRequest, final VentaTarjeta venta, final MappingContext context) {
                LOGGER.info(" #### Custom mapping for VentaTarjetaCreateRequest --> VentaEfectivo #### ");
                Cliente cliente = Cliente.builder()
                        .id(ventaTarjetaRequest.getClienteId())
                        .build();
                Negocio negocio = Negocio.builder()
                		.id(ventaTarjetaRequest.getNegocioId())
                		.build();
                venta.setCliente(cliente);
                venta.setNegocio(negocio);
                venta.setCantidadCuotas(ventaTarjetaRequest.getCantidadCuotas());
        		DateFormat formatearFecha = new SimpleDateFormat(Constantes.FORMATO_FECHA);
                Date fecha;
				try {
					fecha = formatearFecha.parse(ventaTarjetaRequest.getFecha());
	        		venta.setFecha(fecha);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }).register();
        
        // VENTA ITEM INSERT
        
        mapperFactory.classMap(VentaItemCreateRequest.class, Item.class)
        .customize(new CustomMapper<VentaItemCreateRequest, Item>() {
            public void mapAtoB(final VentaItemCreateRequest ventaItemRequest, final Item item, final MappingContext context) {
                LOGGER.info(" #### Custom mapping for VentaItemCreateRequest --> Item #### ");
                
                Prenda prenda = Prenda.builder()
                		.id(ventaItemRequest.getPrendaId())
                		.build();
                item.setPrenda(prenda);
                item.setCantidad(ventaItemRequest.getCantidad());
                
            }
        }).register();
        
        
		
		// Retornameo la instancia del mapper factory
		return mapperFactory.getMapperFacade();
	}
	

}

package ar.edu.davinci.dvds20221cg6;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.davinci.dvds20221cg6.controller.request.ItemInsertRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.ItemUpdateRequest;
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
import ar.edu.davinci.dvds20221cg6.controller.request.PrendaInsertRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.PrendaUpdateRequest;
import ar.edu.davinci.dvds20221cg6.controller.response.PrendaResponse;
import ar.edu.davinci.dvds20221cg6.domain.Prenda;
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
	
	public OrikaConfiguration() {
		objectMapper = new ObjectMapper();
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
			}
		}).register();
		
		mapperFactory.classMap(PrendaInsertRequest.class, Prenda.class)
		.customize(new CustomMapper<PrendaInsertRequest, Prenda>() {
			public void mapAtoB(final PrendaInsertRequest prendaInsertRequest, final Prenda prenda, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for prendaInsertRequest --> Prenda #### ");
				TipoPrenda tipoPrenda = TipoPrenda.valueOf(prendaInsertRequest.getTipo().toUpperCase());
				EstadoPrenda estadoPrenda = EstadoPrenda.valueOf(prendaInsertRequest.getEstado().toUpperCase());
				prenda.setDescripcion(prendaInsertRequest.getDescripcion());
				prenda.setPrecioBase(prendaInsertRequest.getPrecioBase());
				prenda.setTipo(tipoPrenda);
				prenda.setEstado(estadoPrenda);
				
				
			}
		}).register();
		
		mapperFactory.classMap(PrendaUpdateRequest.class, Prenda.class)
		.customize(new CustomMapper<PrendaUpdateRequest, Prenda>() {
			public void mapAtoB(final PrendaUpdateRequest prendaUpdateRequest, final Prenda prenda, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for prendaUpdateRequest --> Prenda #### ");
				TipoPrenda tipoPrenda = TipoPrenda.valueOf(prendaUpdateRequest.getTipo().toUpperCase());
				EstadoPrenda estadoPrenda = EstadoPrenda.valueOf(prendaUpdateRequest.getEstado().toUpperCase());
				prenda.setDescripcion(prendaUpdateRequest.getDescripcion());
				prenda.setPrecioBase(prendaUpdateRequest.getPrecioBase());
				prenda.setTipo(tipoPrenda);
				prenda.setEstado(estadoPrenda);
			}
		}).register();
		
		//NEGOCIO
		
		mapperFactory.classMap(Negocio.class, NegocioResponse.class).byDefault().register();
		
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
				item.setPrenda(prenda);
				item.setCantidad(itemInsertRequest.getCantidad());
			}
		}).register();

		mapperFactory.classMap(ItemUpdateRequest.class, Item.class)
		.customize(new CustomMapper<ItemUpdateRequest, Item>() {
			public void mapAtoB(final ItemUpdateRequest itemUpdateRequest, final Item item, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for itemUpdateRequest --> Item #### ");
				item.setCantidad(itemUpdateRequest.getCantidad());
			}
		}).register();

		mapperFactory.classMap(Item.class, ItemResponse.class)
		.customize(new CustomMapper<Item, ItemResponse>() {
			public void mapAtoB(final Item item, final ItemResponse itemResponse, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for Item --> ItemResponse #### ");
				PrendaResponse prendaResponse = PrendaResponse.builder()
						.id(item.getPrenda().getId())
						.descripcion(item.getPrenda().getDescripcion())
						.tipo(item.getPrenda().getTipo().getDescripcion())
						.precioBase(item.getPrenda().getPrecioBase())
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
				ventaResponse.setNegocio(negocio);

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
							.precioBase(item.getPrenda().getPrecioBase())
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
				ventaResponse.setNegocio(negocio);

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
							.precioBase(item.getPrenda().getPrecioBase())
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

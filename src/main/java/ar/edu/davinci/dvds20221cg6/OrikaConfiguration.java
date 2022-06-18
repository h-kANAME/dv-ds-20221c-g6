package ar.edu.davinci.dvds20221cg6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.davinci.dvds20221cg6.controller.request.PrendaInsertRequest;
import ar.edu.davinci.dvds20221cg6.controller.request.PrendaUpdateRequest;
import ar.edu.davinci.dvds20221cg6.controller.response.PrendaResponse;
import ar.edu.davinci.dvds20221cg6.domain.Prenda;
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

		mapperFactory.classMap(Prenda.class, PrendaInsertRequest.class).byDefault().register();
		mapperFactory.classMap(Prenda.class, PrendaUpdateRequest.class).byDefault().register();

//		mapperFactory.classMap(Prenda.class, PrendaResponse.class).byDefault().register();
		mapperFactory.classMap(Prenda.class, PrendaResponse.class)
		.customize(new CustomMapper<Prenda, PrendaResponse>() {
			public void mapAtoB(final Prenda prenda, final PrendaResponse prendaResponse, final MappingContext context) {
				LOGGER.info(" #### Custom mapping for Prenda --> PrendaResponse #### ");
				prendaResponse.setId(prenda.getId());
				prendaResponse.setDescripcion(prenda.getDescripcion());
				prendaResponse.setTipo(prenda.getTipo().getDescripcion());
				prendaResponse.setPrecioBase(prenda.getPrecioBase());
			}
		}).register();

		// Retornamos la instancia del mapper factory
		return mapperFactory.getMapperFacade();
	}


}

package ar.edu.davinci.dvds20221cg6.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ar.edu.davinci.dvds20221cg6.domain.Prenda;
import ar.edu.davinci.dvds20221cg6.domain.TipoPrenda;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;



@SpringBootTest
@ExtendWith(SpringExtension.class)

class PrendaServiceTest {

	private final Logger LOGGER = LoggerFactory.getLogger(PrendaServiceTest.class);

	@Autowired
	private PrendaService prendaService;

	@Test
	void testListAll() {

		List<Prenda> prendas = prendaService.list();

		LOGGER.info("Prenda size:" + prendas.size());

		assertNotNull(prendas, "Prendas es nulo");
		assertTrue(prendas.size() > 0, "Prendas está vacio");

	}

	@Test
	void testList() {

		Pageable pageable = PageRequest.of(0, 2);
		Page<Prenda> prendas = prendaService.list(pageable);

		LOGGER.info("Prenda size:" + prendas.getSize());
		LOGGER.info("Prenda total page:" + prendas.getTotalPages());

		Pageable pageable1 = PageRequest.of(1, 2);
		Page<Prenda> prendas1 = prendaService.list(pageable1);

		LOGGER.info("Prenda size:" + prendas1.getSize());
		LOGGER.info("Prenda total page:" + prendas1.getTotalPages());

		assertNotNull(prendas, "Prendas es nulo");
		assertTrue(prendas.getSize() > 0, "Prendas está vacio");
	}

	@Test
	void testFindById() {
		try {
			Prenda prenda = prendaService.findById(4L);
			
			assertNotNull(prenda);
			assertEquals(prenda.getDescripcion(), "Pantalon Gabardina Beige");
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testFindById_withError() {
		Exception exception = assertThrows(BusinessException.class, () -> {
		prendaService.findById(0L);
		});		
		
	    String expectedMessage = "No se encontró la prenda con el id: 0";

	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void testSave() {

		LOGGER.info("Prenda count antes insert: " + prendaService.count());
		Prenda prenda = Prenda.builder()
				.descripcion("Tapado Negro")
				.tipo(TipoPrenda.TAPADO)
				.precioBase(BigDecimal.valueOf(234.54D))
				.build();

		Prenda prendaCreada;
		try {
			prendaCreada = prendaService.save(prenda);
			
			assertNotNull(prendaCreada);
			assertEquals(prenda.getDescripcion(), prendaCreada.getDescripcion());
			
			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		LOGGER.info("Prenda count después insert: " + prendaService.count());

	}

	@Test
	void testDelete() {

		LOGGER.info("Prenda count antes delete: " + prendaService.count());
		Prenda prenda = null;
		try {
			prenda = prendaService.findById(2L);
			LOGGER.info("Prenda la delete: " + prenda.toString());
			
			prendaService.delete(prenda);

			
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LOGGER.info("Prenda count después insert: " + prendaService.count());

	}

}

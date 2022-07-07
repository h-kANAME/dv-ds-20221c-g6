package ar.edu.davinci.dvds20221cg6.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ar.edu.davinci.dvds20221cg6.domain.Cliente;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ClienteRepositoryTest {

	
	private final Logger LOGGER = LoggerFactory.getLogger(ClienteRepositoryTest.class);
	
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Test
	void testFindAll() {
		assertNotNull(clienteRepository, "El repositorio es nulo.");
		List<Cliente> clientes = clienteRepository.findAll();
		
		LOGGER.info("Clientes encontradas: " + clientes.size());

		assertNotNull(clientes, "La lista de clientes es nula.");
		assertTrue(clientes.size() > 0, "No existen clientes.");
		
	}

	@Test
	void testFindById() {
		Long id = 4L;
		Cliente cliente = null;
		Optional<Cliente> clienteOpcional = clienteRepository.findById(id);
		if (clienteOpcional.isPresent()){
			cliente = clienteOpcional.get();
			
			LOGGER.info("Cliente encontrada: " + cliente);
			
		} else {
			LOGGER.info("Cliente no encontrada, para el id: " + id);
			assertNull(cliente);
			
		}
	}

}


package ar.edu.davinci.dvds20221cg6.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.dvds20221cg6.domain.Cliente;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;

public interface ClienteService {
	
	// Métodos de creación, modificación y borrado.
	Cliente save(Cliente cliente) throws BusinessException;
	Cliente update(Cliente cliente) throws BusinessException;
	void delete(Cliente cliente);
	void delete(Long id);

	// Método de búsqueda.
	Cliente findById(Long id) throws BusinessException;
	
	
	// Método de listado.
	List<Cliente> list();
	Page<Cliente> list(Pageable pageable);
	
	// Método para contar cantidad de datos.
	long count();


}


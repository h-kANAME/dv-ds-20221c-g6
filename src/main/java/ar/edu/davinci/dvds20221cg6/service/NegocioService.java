package ar.edu.davinci.dvds20221cg6.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.dvds20221cg6.domain.Venta;

public interface NegocioService {
	// Método de listado.
		List<Venta> list();
		Page<Venta> list(Pageable pageable);
}

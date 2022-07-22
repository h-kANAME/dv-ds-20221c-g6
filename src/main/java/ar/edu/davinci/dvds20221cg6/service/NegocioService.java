package ar.edu.davinci.dvds20221cg6.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.dvds20221cg6.domain.Negocio;
import ar.edu.davinci.dvds20221cg6.domain.Venta;
import ar.edu.davinci.dvds20221cg6.exception.BusinessException;

public interface NegocioService {

	Negocio addVenta(Long negocioId, Venta venta) throws BusinessException;

	Page<Venta> list(Pageable pageable);

}

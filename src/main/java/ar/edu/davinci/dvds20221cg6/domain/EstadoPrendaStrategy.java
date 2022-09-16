package ar.edu.davinci.dvds20221cg6.domain;

import java.math.BigDecimal;

public interface EstadoPrendaStrategy {
	
	public BigDecimal obtenerPrecioVenta(BigDecimal precioBase);
}

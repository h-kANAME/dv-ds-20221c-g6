package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class PrendaNueva implements Serializable, EstadoPrendaStrategy{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2779732363036960652L;
	
	@Override
	public BigDecimal obtenerPrecioVenta(BigDecimal precioBase) {
		// TODO Auto-generated method stubs
		return precioBase;
	}

}

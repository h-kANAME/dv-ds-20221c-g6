package ar.edu.davinci.dvds20221cg6.domain;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;

public class PrendaPromocion implements EstadoPrendaStrategy{
	
	public Double descuento;
	
	@Override
	public BigDecimal obtenerPrecioVenta(BigDecimal precioBase) {
		// TODO Auto-generated method stub
		return null;
	}

}

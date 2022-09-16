package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class PrendaLiquidacion implements EstadoPrendaStrategy{

	private static final Double PORCENTAJE= 50.0;

	
	@Override
	public BigDecimal obtenerPrecioVenta(BigDecimal precioBase) {
		// TODO Auto-generated method stub
		return precioBase.multiply(new BigDecimal(PORCENTAJE).divide(new BigDecimal(100)));
	}

}

package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;
import java.math.BigDecimal;


public class PrendaPromocion implements Serializable, EstadoPrendaStrategy{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8332826439098689194L;
	
	private Double descuento = 20.0;
	
	@Override
	public BigDecimal obtenerPrecioVenta(BigDecimal precioBase) {
		// TODO Auto-generated method stub
		BigDecimal montoRestar = precioBase.multiply(new BigDecimal(descuento))
				.divide(new BigDecimal(100));

		return precioBase.subtract(montoRestar);
	}

}

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
	public void obtenerPrecioVenta(Prenda prenda) {
		// TODO Auto-generated method stub
		prenda.setEstado(EstadoPrenda.PROMOCION);
		BigDecimal resta = prenda.getPrecioBase()
								.multiply(new BigDecimal(descuento))
								.divide(new BigDecimal(100));
		BigDecimal precioFinal = prenda.getPrecioBase().subtract(resta);
		prenda.setPrecioFinal(precioFinal);
	}

}

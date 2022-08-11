package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class PrendaLiquidacion implements Serializable, EstadoPrendaStrategy{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -719037688500501607L;
	
		
	private static final Double PORCENTAJE= 50.0;

	
	@Override
	public void obtenerPrecioVenta(Prenda prenda) {
		// TODO Auto-generated method stub
		prenda.setEstado(EstadoPrenda.LIQUIDACION);
		BigDecimal precioFinal = prenda.getPrecioBase()
								.multiply(new BigDecimal(PORCENTAJE))
								.divide(new BigDecimal(100));
		prenda.setPrecioFinal(precioFinal);
	}

}

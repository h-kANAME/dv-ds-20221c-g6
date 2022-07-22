package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;

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

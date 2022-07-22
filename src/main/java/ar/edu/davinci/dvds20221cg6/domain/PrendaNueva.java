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

public class PrendaNueva implements Serializable, EstadoPrendaStrategy{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2779732363036960652L;
	
	@Override
	public void obtenerPrecioVenta(Prenda prenda) {
		// TODO Auto-generated method stubs
		prenda.setEstado(EstadoPrenda.NUEVA);
		prenda.setPrecioFinal(prenda.getPrecioBase());;
	}

}

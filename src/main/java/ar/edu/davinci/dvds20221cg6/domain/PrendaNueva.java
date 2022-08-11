package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;

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

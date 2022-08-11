package ar.edu.davinci.dvds20221cg6.domain;

import java.util.LinkedList;
import java.util.List;

public enum EstadoPrenda {
	
	NUEVA("Nueva"),
	LIQUIDACION("Liquidacion"),
	PROMOCION("Promocion");
	
	private String descripcion;
	
	private EstadoPrenda(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public static List<EstadoPrenda> getEstadoPrendas(){
		List<EstadoPrenda> estadoPrendas = new LinkedList<EstadoPrenda>();
		estadoPrendas.add(EstadoPrenda.NUEVA);
		estadoPrendas.add(EstadoPrenda.LIQUIDACION);
		estadoPrendas.add(EstadoPrenda.PROMOCION);
		return estadoPrendas;
	}
}

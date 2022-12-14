package ar.edu.davinci.dvds20221cg6.domain;

import java.util.LinkedList;
import java.util.List;

public enum EstadoPrenda {

	NUEVA("Nueva", new PrendaNueva()),
	LIQUIDACION("Liquidacion", new PrendaLiquidacion()),
	PROMOCION("Promocion", new PrendaPromocion());

	private String descripcion;
	private EstadoPrendaStrategy strategy;

	private EstadoPrenda(String descripcion, EstadoPrendaStrategy strategy) {
		this.descripcion = descripcion;
		this.strategy = strategy;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public EstadoPrendaStrategy getStrategy() {
		return strategy;
	}

	public static List<EstadoPrenda> getEstadoPrendas(){
		List<EstadoPrenda> estadoPrendas = new LinkedList<EstadoPrenda>();
		estadoPrendas.add(EstadoPrenda.NUEVA);
		estadoPrendas.add(EstadoPrenda.LIQUIDACION);
		estadoPrendas.add(EstadoPrenda.PROMOCION);
		return estadoPrendas;
	}
}

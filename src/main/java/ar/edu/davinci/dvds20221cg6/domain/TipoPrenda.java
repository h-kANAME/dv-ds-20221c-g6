package ar.edu.davinci.dvds20221cg6.domain;

import java.util.LinkedList;
import java.util.List;

public enum TipoPrenda {

	SACO("Saco"),
	PANTALON("Pantalon"),
	CAMISA("Camisa"),
	CAMPERA("Campera"),
	TAPADO("Tapado"),
	CHAQUETA("Chaqueta"),
	MEDIA("Media"),
	BUFANDA("Bufanda");

	private String descripcion;

	private TipoPrenda(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public static List<TipoPrenda> getTipoPrendas() {
		List<TipoPrenda> tipoPrendas = new LinkedList<TipoPrenda>();
		tipoPrendas.add(TipoPrenda.SACO);
		tipoPrendas.add(TipoPrenda.PANTALON);
		tipoPrendas.add(TipoPrenda.CAMISA);
		tipoPrendas.add(TipoPrenda.CAMPERA);
		tipoPrendas.add(TipoPrenda.TAPADO);
		tipoPrendas.add(TipoPrenda.CHAQUETA);
		tipoPrendas.add(TipoPrenda.MEDIA);
		tipoPrendas.add(TipoPrenda.BUFANDA);
		
		return tipoPrendas;
	}


}

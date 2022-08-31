package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Configuración inical de JPA de una entidad
@Entity
@Table(name="stock")

//Configuración de Lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class Stock implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 6956738159483741762L;
	
	// Configurar por JPA cual el PK de la tabla prendas
		@Id
		// Configurar la estragia de generación de los ids por JPA
		@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
		@GenericGenerator(name = "native", strategy = "native")
		// Configuramos por JPA el nombre de la columna
		@Column(name = "stock_id")
		private Long id;
		
		@Column(name = "prd_cantidad")
		private Integer cantidad;

		public void agregarStock(Integer cantidad2) {
			this.cantidad+=cantidad2;
			
		}
		
		public void descontarStock(Integer cantidad2) {
			this.cantidad-=cantidad2;
			
		}
	

}
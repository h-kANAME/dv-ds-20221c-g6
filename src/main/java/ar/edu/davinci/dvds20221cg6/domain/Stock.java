
package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="stocks")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Stock implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 6956738159483741762L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "stk_id")
	private Long id;
	
	@Column(name = "stk_cantidad")
	private Integer cantidad;
	
	public Integer getCantidad() {
		return cantidad;
	}
	
	public void agregarStock(Integer cantidad) {
		this.cantidad += cantidad;
	}
	
	public void descontarStock(Integer cantidad) {
		this.cantidad -= cantidad;
	}
}

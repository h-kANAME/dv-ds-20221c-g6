package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="negocio")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Negocio implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3929492102236150035L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ngo_id")
	private Long id;
	
	@OneToMany(mappedBy="negocio", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference
	private List<Venta> ventas;
	
	public BigDecimal calcularGananciaPorDia(Date dia) {
		BigDecimal gananciaXDia = new BigDecimal(0.0);
		ventas.stream().forEach(v -> {if(v.esDeFecha(dia)) { gananciaXDia.add(v.importeFinal());}});
		return gananciaXDia;
	}
}

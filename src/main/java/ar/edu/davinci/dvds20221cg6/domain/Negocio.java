package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

//Configuración inicial de JPA de la entidad prendas
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name="negocio")

//Configuración de lombok
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Negocio implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6403406151487634545L;

	// Configuración por JPA de la PK de la tabla
	@Id
	// Configuración por JPA de la manera en que se generan los IDs autogenerados en MySQL
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	// Configuración por JPA del nombre de la columna
	@Column(name = "ngo_id")
	private Long id;
	
	@OneToMany(mappedBy="negocio", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference
	private List<Venta> ventas;
	
	public BigDecimal calcuarGananciaPorDia(Date dia) {
		Double suma = ventas.stream()
				.collect(Collectors.summingDouble(venta -> venta.esDeFecha(dia) ? venta.importeFinal().doubleValue() : 0.0 ));
		return new BigDecimal(suma).setScale(2, RoundingMode.UP);
	};
}

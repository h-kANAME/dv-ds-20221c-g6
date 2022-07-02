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

@Entity
@Table(name="clientes")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Cliente implements Serializable {

	
	private static final long serialVersionUID = 6363777413501451503L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "cli_id")
	private Long id;
	
	@Column(name = "cli_nombre")
	private String nombre;
	
	@Column(name = "cli_apellido")
	private String apellido;
	
	
	public String getRazonSocial() {
		return nombre + " " + apellido;
	}

}

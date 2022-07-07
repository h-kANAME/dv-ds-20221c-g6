package ar.edu.davinci.dvds20221cg6.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Calcular Venta en Efectivo
 * 
 * @author frmontero
 *
 */

@Entity
@PrimaryKeyJoinColumn(name = "vta_id")
@DiscriminatorValue("EFECTIVO")
@Table(name="ventas_efectivo")


@Data
@NoArgsConstructor(force = true)
@SuperBuilder
public class VentaEfectivo extends Venta implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8393218825317899807L;

	@Override
	public Double conRecargo(Double importeBase) {
		return importeBase;
	}

}


package ar.edu.davinci.dvds20221cg6.controller.request;

import java.math.BigDecimal;

import ar.edu.davinci.dvds20221cg6.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrendaUpdateRequest {

	private String descripcion;

	private String tipo;
	
	private String estado;

	private BigDecimal precioBase;
	
}

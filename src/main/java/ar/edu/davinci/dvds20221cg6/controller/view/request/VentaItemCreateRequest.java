package ar.edu.davinci.dvds20221cg6.controller.view.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaItemCreateRequest {

	private Long ventaId;

	private Integer cantidad;

	private Long prendaId;
	

}


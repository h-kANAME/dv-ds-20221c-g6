package ar.edu.davinci.dvds20221cg6.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaEfectivoRequest {
	
	private Long clienteId;
	
	private Long negocioId;
	
}


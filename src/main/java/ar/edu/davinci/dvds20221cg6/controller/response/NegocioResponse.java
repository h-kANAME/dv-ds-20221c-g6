package ar.edu.davinci.dvds20221cg6.controller.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NegocioResponse {
	private Long id;
	
	private String name;
	
	private List<VentaResponse> ventas;
	
	private BigDecimal gananciaTotal;
}

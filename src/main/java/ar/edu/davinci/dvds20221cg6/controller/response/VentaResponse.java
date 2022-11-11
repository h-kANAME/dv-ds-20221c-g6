package ar.edu.davinci.dvds20221cg6.controller.response;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@CrossOrigin("*")
@RestController
public abstract class VentaResponse {

	private Long id;
	
	private ClienteResponse cliente;
	
	private String fecha;
	
	private Long idNegocio;
	
	private List<ItemResponse> items;
	
	private BigDecimal importeFinal;


}

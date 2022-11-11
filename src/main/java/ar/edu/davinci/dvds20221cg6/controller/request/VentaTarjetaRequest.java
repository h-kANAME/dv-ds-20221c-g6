package ar.edu.davinci.dvds20221cg6.controller.request;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@CrossOrigin("*")
@RestController
public class VentaTarjetaRequest {
	
	private Long clienteId;
	
	private Long negocioId;
	
	private Integer cantidadCuotas;

}


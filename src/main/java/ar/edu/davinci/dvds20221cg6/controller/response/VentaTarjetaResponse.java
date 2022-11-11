package ar.edu.davinci.dvds20221cg6.controller.response;

import java.math.BigDecimal;

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
public class VentaTarjetaResponse extends VentaResponse {

	private Integer cantidadCuotas;
	
	private BigDecimal coeficienteTarjeta;
}

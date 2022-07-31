package ar.edu.davinci.dvds20221cg6.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockResponse {
	private Long id;
	
	private Integer cantidad;
}

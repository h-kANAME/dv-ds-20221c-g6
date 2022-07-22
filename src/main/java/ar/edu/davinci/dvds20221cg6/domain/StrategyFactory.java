package ar.edu.davinci.dvds20221cg6.domain;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class StrategyFactory {
	
	private Map<EstadoPrenda, EstadoPrendaStrategy> strategies = new EnumMap<>(EstadoPrenda.class);
	
	public StrategyFactory() {
		initStrategies();
	}

	private void initStrategies() {
		// TODO Auto-generated method stub
		strategies.put(EstadoPrenda.LIQUIDACION, new PrendaLiquidacion());
		strategies.put(EstadoPrenda.PROMOCION, new PrendaPromocion());
		strategies.put(EstadoPrenda.NUEVA, new PrendaNueva());
	}
	
	public EstadoPrendaStrategy getStrategy(EstadoPrenda estadoPrenda) {
		if(estadoPrenda == null || !strategies.containsKey(estadoPrenda)) {
			throw new IllegalArgumentException("Invalid " + estadoPrenda);
		}
		return strategies.get(estadoPrenda);
	}

}

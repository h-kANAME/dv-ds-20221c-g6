package ar.edu.davinci.dvds20221cg6.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.davinci.dvds20221cg6.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{

}

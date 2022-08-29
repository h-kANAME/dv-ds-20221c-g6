package ar.edu.davinci.dvds20221cg6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.davinci.dvds20221cg6.domain.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

}

package ar.edu.davinci.dvds20221cg6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.davinci.dvds20221cg6.domain.Negocio;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long>{

}

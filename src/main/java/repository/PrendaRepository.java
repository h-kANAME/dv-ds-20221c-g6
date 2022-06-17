package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Prenda;

@Repository
public interface PrendaRepository extends JpaRepository<Prenda, Long> {

}



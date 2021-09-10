package com.projetobetha.dev.repositories;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}

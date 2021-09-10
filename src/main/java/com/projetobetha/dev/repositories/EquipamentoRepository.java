package com.projetobetha.dev.repositories;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.Equipamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Integer> {

}

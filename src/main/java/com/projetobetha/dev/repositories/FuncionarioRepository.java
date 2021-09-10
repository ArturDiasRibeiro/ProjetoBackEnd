package com.projetobetha.dev.repositories;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    @Transactional(readOnly = true)
    Funcionario findByEmail(String email);

}

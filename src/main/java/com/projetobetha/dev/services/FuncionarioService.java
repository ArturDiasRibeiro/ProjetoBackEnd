package com.projetobetha.dev.services;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.Funcionario;
import com.projetobetha.dev.repositories.FuncionarioRepository;
import com.projetobetha.dev.services.exceptions.ObjectNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario find(Integer id) {
        Optional<Funcionario> obj = funcionarioRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Funcionario.class.getName()));
    }

    public Funcionario findByEmail(Funcionario obj) {
        return funcionarioRepository.findByEmail(obj.getEmail());
    }

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario insert(Funcionario obj) {
        obj.setSenha(bCryptPasswordEncoder.encode(obj.getSenha()));
        return funcionarioRepository.save(obj);
    }

    //PUT
    public Funcionario update(Funcionario obj) {
        find(obj.getId());
        obj.setSenha(bCryptPasswordEncoder.encode(obj.getSenha()));
        return funcionarioRepository.save(obj);
    }

    //DELETE BY ID
    public void delete(Integer id) {
        find(id);
        try {
            funcionarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir um funcionario que possui uma ordem de servico", e);
        }
    }
}

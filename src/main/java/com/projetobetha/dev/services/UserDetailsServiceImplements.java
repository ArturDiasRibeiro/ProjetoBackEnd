package com.projetobetha.dev.services;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.Funcionario;
import com.projetobetha.dev.domain.enums.Perfil;
import com.projetobetha.dev.repositories.FuncionarioRepository;
import com.projetobetha.dev.security.UserSS;
import java.util.Collection;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplements implements UserDetailsService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Funcionario funcionario = funcionarioRepository.findByEmail(email);
        if (funcionario == null) {
            throw new UsernameNotFoundException(email);
        }
        Collection<Perfil> perfil = Collections.singleton(funcionario.getPerfil());
        return new UserSS(funcionario.getId(), funcionario.getEmail(), funcionario.getSenha(), perfil);
    }

}

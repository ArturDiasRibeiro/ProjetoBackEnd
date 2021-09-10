package com.projetobetha.dev.services;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.Cliente;
import com.projetobetha.dev.domain.Equipamento;
import com.projetobetha.dev.domain.Funcionario;
import com.projetobetha.dev.domain.OrdemDeServico;
import com.projetobetha.dev.domain.enums.Perfil;
import com.projetobetha.dev.domain.enums.StatusDaOrdem;
import com.projetobetha.dev.repositories.ClienteRepository;
import com.projetobetha.dev.repositories.EquipamentoRepository;
import com.projetobetha.dev.repositories.FuncionarioRepository;
import com.projetobetha.dev.repositories.OrdemDeServicoRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OrdemDeServicoRepository ordemDeServicoRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    public void instantiateTestDatabase() {

        Funcionario func = new Funcionario("Artur", "ADMIN@email.com", bCryptPasswordEncoder.encode("123"), Perfil.ADMIN);
        Funcionario func2 = new Funcionario("Maria", "ATENDENTE@email.com", bCryptPasswordEncoder.encode("123"), Perfil.ATENDENTE);
        Funcionario func3 = new Funcionario("João", "TECNICO@email.com", bCryptPasswordEncoder.encode("123"), Perfil.TECNICO);
        funcionarioRepository.saveAll(Arrays.asList(func, func2, func3));

        Cliente cli1 = new Cliente("Frederico Pereira", "FP@Gmail.com", "(55) 5555-5555", "Rua Avenida Rua");
        Cliente cli2 = new Cliente("Jorge da Silva", "JS@Gmail.com", "(55) 5555-5555", "Avenida Rua Avenida");
        clienteRepository.saveAll(Arrays.asList(cli1, cli2));

        OrdemDeServico ordem1 = new OrdemDeServico(cli1, BigDecimal.valueOf(499.00));
        OrdemDeServico ordem2 = new OrdemDeServico(cli2, BigDecimal.valueOf(199.50));
        ordem1.setStatus(StatusDaOrdem.CONCLUIDO);
        ordem2.setStatus(StatusDaOrdem.APROVADO);
        ordemDeServicoRepository.saveAll(Arrays.asList(ordem1, ordem2));

        Equipamento eq1 = new Equipamento("50U6305", "AOC", "Televisor", "Tela Preta");
        Equipamento eq2 = new Equipamento("M21s", "Samsung", "Celular", "Não Liga");
        Equipamento eq3 = new Equipamento("Hs50", "Corsair", "Headset", "Não sai som");

        eq1.setOrdem(ordem1);
        eq2.setOrdem(ordem1);
        eq3.setOrdem(ordem2);
        equipamentoRepository.saveAll(Arrays.asList(eq1, eq2, eq3));
    }
}


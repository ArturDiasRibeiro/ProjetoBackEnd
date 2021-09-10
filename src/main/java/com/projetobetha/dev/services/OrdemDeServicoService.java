package com.projetobetha.dev.services;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.OrdemDeServico;
import com.projetobetha.dev.domain.enums.StatusDaOrdem;
import com.projetobetha.dev.dto.OrdemDeServicoDTO;
import com.projetobetha.dev.repositories.OrdemDeServicoRepository;
import com.projetobetha.dev.services.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.projetobetha.dev.domain.Cliente;
import com.projetobetha.dev.domain.Equipamento;
import com.projetobetha.dev.dto.OrdemDeServicoNewDTO;
import com.projetobetha.dev.repositories.EquipamentoRepository;
import java.util.ArrayList;
import java.util.Iterator;

@Service
public class OrdemDeServicoService {

    @Autowired
    private OrdemDeServicoRepository ordemDeServicoRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    //GET BY ID
    public OrdemDeServico find(Integer id) {
        Optional<OrdemDeServico> obj = ordemDeServicoRepository.findById(id);
        if (obj == null || obj.isEmpty()) {
            throw new ObjectNotFoundException("Obejto não encontrado! Id: " + id + ", Tipo: " + OrdemDeServico.class.getTypeName());
        }
        return obj.orElse(null);
    }

    //GET ALL
    public List<OrdemDeServico> findAll() {
        return ordemDeServicoRepository.findAll();
    }

    //POST
    public OrdemDeServico insert(OrdemDeServicoNewDTO objNewDto) {

        Cliente cli = clienteService.find(objNewDto.getClienteId());
        List<Equipamento> equipamentos = new ArrayList<>();
        OrdemDeServico ordem = new OrdemDeServico(cli, objNewDto.getValor());

        if (objNewDto.getEquipamentos().equals(null) || objNewDto.getEquipamentos().isEmpty()) {
            throw new DataIntegrityViolationException("O EQUIPAMENTO NÃO PODE ESTAR VAZIO");
        } else {
            ordemDeServicoRepository.save(ordem);
            equipamentos = objNewDto.getEquipamentos();
            for (Equipamento eq : equipamentos) {
                eq.setOrdem(ordem);
                equipamentoRepository.save(eq);
            }
        }
        ordem.setStatus(StatusDaOrdem.PENDENTE);
        return ordemDeServicoRepository.save(ordem);
    }

    //PUT
    public OrdemDeServico update(OrdemDeServicoDTO objDto, Integer id) {

        OrdemDeServico obj = find(id);

        Cliente cli = clienteService.find(objDto.getClienteId());
        obj.setCliente(cli);

        for (Iterator<Equipamento> iterator = obj.getEquipamentos().iterator(); iterator.hasNext();) {
            Equipamento original = iterator.next();

            for (Equipamento novo : objDto.getEquipamentos()) {
                System.out.println(obj.getEquipamentos().size());
                if (novo.getId().equals(original.getId())) {
                    original.setModelo(novo.getModelo());
                    original.setMarca(novo.getMarca());
                    original.setClassificacaoDoProduto(novo.getClassificacaoDoProduto());
                    original.setAvarias(novo.getAvarias());
                    original.setImagemUrl(novo.getImagemUrl());
                    original.setOrdem(obj);
                } else {
                    iterator.remove();
                }
            }
        }

        obj.setValor(objDto.getValor());

        if (objDto.getStatus() != null) {
            obj.setStatus(objDto.getStatus());
        }

        if (obj.getStatus().equals(StatusDaOrdem.AGUARDANDOCLIENTE)) {
            emailService.sendConfirmationHtmlEmail(obj, objDto);
        }

        if (obj.getStatus().equals(StatusDaOrdem.CONCLUIDO)) {
            emailService.sendConclusionHtmlEmail(obj);
        }

        //ordemDeServicoRepository.save(obj);
        return ordemDeServicoRepository.save(obj);
    }

    //ORDER STATUS
    public OrdemDeServico updateAprovada(Integer id) {
        OrdemDeServico obj = find(id);
        if (!obj.getStatus().equals(StatusDaOrdem.RECUSADO)) {
            obj.setStatus(StatusDaOrdem.APROVADO);
        } else {
            obj.setStatus(StatusDaOrdem.RECUSADO);
            throw new DataIntegrityViolationException("Você já recusou esta ordem, se desejar alterar, favor entrar em contato com a empresa.");
        }
        return ordemDeServicoRepository.save(obj);
    }

    //ORDER STATUS
    public OrdemDeServico updateReprovada(Integer id) {
        OrdemDeServico obj = find(id);
        if (!obj.getStatus().equals(StatusDaOrdem.APROVADO)) {
            obj.setStatus(StatusDaOrdem.RECUSADO);
        } else {
            obj.setStatus(StatusDaOrdem.APROVADO);
            throw new DataIntegrityViolationException("Você já aprovou esta ordem, se desejar alterar, favor entrar em contato com a empresa.");
        }
        return ordemDeServicoRepository.save(obj);
    }

    //PATCH TO CONCLUDE ORDER
    public OrdemDeServico updateConclusao(OrdemDeServico obj) {
        find(obj.getId());
        if (obj.getStatus().equals(StatusDaOrdem.APROVADO) || obj.getStatus().equals(StatusDaOrdem.CONCLUIDO)) {
            if (obj.getStatus().equals(StatusDaOrdem.CONCLUIDO)) {
                obj.setStatus(StatusDaOrdem.CONCLUIDO);
            } else {
                obj.setStatus(StatusDaOrdem.CONCLUIDO);
                emailService.sendConclusionHtmlEmail(obj);
            }
        } else {
            obj.setStatus(StatusDaOrdem.RECUSADO);
        }
        return ordemDeServicoRepository.save(obj);
    }

    //DELETE BY ID
    public void delete(Integer id) {
        find(id);
        try {
            ordemDeServicoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir uma ordem de serviço que possui um cliente", e);
        }
    }

    public OrdemDeServico newDTO(OrdemDeServicoNewDTO objDto) {
        OrdemDeServico ordem = new OrdemDeServico();
        Cliente cli = clienteService.find(objDto.getClienteId());

        ordem.setCliente(cli);
        ordem.setStatus(StatusDaOrdem.PENDENTE);

        return ordem;
    }
}

package com.projetobetha.dev.dto;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.Equipamento;
import com.projetobetha.dev.domain.enums.StatusDaOrdem;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrdemDeServicoNewDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer clienteId;
    private List<Equipamento> equipamentos;
    private BigDecimal valor;
    private StatusDaOrdem status;

    public OrdemDeServicoNewDTO() {
    }

    public OrdemDeServicoNewDTO(Integer clienteId, BigDecimal valor) {
        this.clienteId = clienteId;
        this.valor = valor;

    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public StatusDaOrdem getStatus() {
        return status;
    }

    public void setStatus(StatusDaOrdem status) {
        this.status = status;
    }
}

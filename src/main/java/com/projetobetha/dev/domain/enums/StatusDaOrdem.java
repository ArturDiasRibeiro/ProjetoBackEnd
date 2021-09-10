package com.projetobetha.dev.domain.enums;

// Coded by: Artur Dias
public enum StatusDaOrdem {

    PENDENTE(0, "Ordem de Serviço ainda pendente"),
    AGUARDANDOCLIENTE(1, "Ordem de Serviço está esperando resposta do cliente"),
    APROVADO(2, "Ordem de Serviço aprovada pelo cliente"),
    CONCLUIDO(3, "Ordem de Serviço concluída"),
    RECUSADO(4, "Ordem de Serviço recusada");

    private int cod;
    private String descricao;

    private StatusDaOrdem(int cod, String descricao) {
        this.cod = cod;
        this.descricao = descricao;
    }

    public int getCod() {
        return cod;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusDaOrdem toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (StatusDaOrdem x : StatusDaOrdem.values()) {
            if (cod.equals(x.getCod())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Id inválido: " + cod);
    }

}

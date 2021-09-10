package com.projetobetha.dev.dto;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.OrdemDeServico;

public class EquipamentoDTO {

    private String modelo;
    private String marca;
    private String classificacaoDoProduto;
    private String avarias;

    private String imagemUrl;
    private OrdemDeServico ordem;

    public EquipamentoDTO() {
    }

    public EquipamentoDTO(String modelo, String marca, String classificacaoDoProduto, String avarias, String imagemUrl) {
        this.modelo = modelo;
        this.marca = marca;
        this.classificacaoDoProduto = classificacaoDoProduto;
        this.avarias = avarias;
        this.imagemUrl = imagemUrl;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getAvarias() {
        return avarias;
    }

    public void setAvarias(String avarias) {
        this.avarias = avarias;
    }

    public String getClassificacaoDoProduto() {
        return classificacaoDoProduto;
    }

    public void setClassificacaoDoProduto(String classificacaoDoProduto) {
        this.classificacaoDoProduto = classificacaoDoProduto;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public OrdemDeServico getOrdem() {
        return ordem;
    }

    public void setOrdem(OrdemDeServico ordem) {
        this.ordem = ordem;
    }

}

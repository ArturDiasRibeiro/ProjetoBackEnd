package com.projetobetha.dev.domain;

//Coded by: Artur Dias
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Equipamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "Preencha todas as informações do Equipamento")
    private String modelo;
    @NotBlank(message = "Preencha todas as informações do Equipamento")
    private String marca;
    @NotBlank(message = "Preencha todas as informações do Equipamento")
    private String classificacaoDoProduto;
    @NotBlank(message = "Preencha todas as informações do Equipamento")
    private String avarias;

    private String imagemUrl;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ordem_id")
    private OrdemDeServico ordem;

    public Equipamento() {
    }

    public Equipamento(String modelo, String marca, String classificacaoDoProduto, String avarias) {
        this.modelo = modelo;
        this.marca = marca;
        this.classificacaoDoProduto = classificacaoDoProduto;
        this.avarias = avarias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Equipamento other = (Equipamento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}

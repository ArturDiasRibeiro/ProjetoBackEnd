package com.projetobetha.dev.services;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.Equipamento;
import com.projetobetha.dev.repositories.EquipamentoRepository;
import com.projetobetha.dev.services.exceptions.ObjectNotFoundException;
import java.net.URI;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EquipamentoService {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private S3Service s3Service;

    public Equipamento find(Integer id) {
        Optional<Equipamento> obj = equipamentoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Equipamento.class.getName()));
    }

    public URI uploadFotosEquipamento(MultipartFile multipartFile, Integer id) {
        URI uri = s3Service.uploadFile(multipartFile, id);
        Equipamento eq = find(id);
        eq.setImagemUrl(uri.toString());
        equipamentoRepository.save(eq);
        return uri;
    }
}

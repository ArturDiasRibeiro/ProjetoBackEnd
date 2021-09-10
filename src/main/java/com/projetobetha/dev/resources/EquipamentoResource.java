package com.projetobetha.dev.resources;

//Coded by: Artur Dias
import com.projetobetha.dev.services.EquipamentoService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/equipamentos")
public class EquipamentoResource {

    @Autowired
    private EquipamentoService equipamentoService;

    @PreAuthorize("hasAnyRole('TECNICO', 'ADMIN')")
    @PostMapping(value = "/uploadfotos/{id}")
    public ResponseEntity<Void> uploadFotosEquipamento(@RequestParam(name = "file") MultipartFile file, @PathVariable Integer id) {
        URI uri = equipamentoService.uploadFotosEquipamento(file, id);
        return ResponseEntity.created(uri).build();
    }
}

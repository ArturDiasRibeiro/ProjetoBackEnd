package com.projetobetha.dev.resources;

//Coded by: Artur Dias
import com.projetobetha.dev.domain.OrdemDeServico;
import com.projetobetha.dev.dto.OrdemDeServicoDTO;
import com.projetobetha.dev.dto.OrdemDeServicoNewDTO;
import com.projetobetha.dev.services.OrdemDeServicoService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/ordemdeservicos")
public class OrdemDeServicoResource {

    @Autowired
    private OrdemDeServicoService ordemDeServicoService;

    @PreAuthorize("hasAnyRole('ATENDENTE', 'TECNICO', 'ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrdemDeServico> find(@PathVariable Integer id) {
        OrdemDeServico obj = ordemDeServicoService.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ATENDENTE', 'TECNICO', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrdemDeServico>> findAll() {
        List<OrdemDeServico> list = ordemDeServicoService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PreAuthorize("hasAnyRole('ATENDENTE', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody OrdemDeServicoNewDTO objDTO) {
        OrdemDeServico obj = ordemDeServicoService.insert(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('TECNICO', 'ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<OrdemDeServico> update(@RequestBody OrdemDeServicoDTO obj, @PathVariable Integer id) {
        OrdemDeServico ordem = ordemDeServicoService.update(obj, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/ordemaprovada/{id}")
    public ResponseEntity<OrdemDeServico> updateAprovada(@PathVariable Integer id) {
        OrdemDeServico obj = ordemDeServicoService.updateAprovada(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/ordemrecusada/{id}")
    public ResponseEntity<OrdemDeServico> updateReprovada(@PathVariable Integer id) {
        OrdemDeServico obj = ordemDeServicoService.updateReprovada(id);
        return ResponseEntity.noContent().build();

    }

    @PreAuthorize("hasAnyRole('TECNICO', 'ADMIN')")
    @PostMapping(value = "/servicorealizado/{id}")
    public ResponseEntity<OrdemDeServico> updateConclusao(@PathVariable Integer id) {
        OrdemDeServico obj = ordemDeServicoService.find(id);
        obj = ordemDeServicoService.updateConclusao(obj);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ordemDeServicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

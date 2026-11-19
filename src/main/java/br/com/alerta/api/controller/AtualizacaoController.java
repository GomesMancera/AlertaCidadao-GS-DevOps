package br.com.alerta.api.controller;

import br.com.alerta.api.dto.AtualizacaoDtos;
import br.com.alerta.api.service.AtualizacaoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atualizacoes")
public class AtualizacaoController {

    private final AtualizacaoService atualizacaoService;

    public AtualizacaoController(AtualizacaoService atualizacaoService) {
        this.atualizacaoService = atualizacaoService;
    }

    @PostMapping
    public AtualizacaoDtos.Response create(@Valid @RequestBody AtualizacaoDtos.CreateRequest request) {
        return atualizacaoService.create(request);
    }

    @GetMapping
    public List<AtualizacaoDtos.Response> list(@RequestParam(required = false) Long alertaId) {
        if (alertaId != null) {
            return atualizacaoService.listByAlerta(alertaId);
        }
        return atualizacaoService.listAll();
    }

    @GetMapping("/{id}")
    public AtualizacaoDtos.Response get(@PathVariable Long id) {
        return atualizacaoService.get(id);
    }

    @PutMapping("/{id}")
    public AtualizacaoDtos.Response update(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoDtos.UpdateRequest request
    ) {
        return atualizacaoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        atualizacaoService.delete(id);
    }
}

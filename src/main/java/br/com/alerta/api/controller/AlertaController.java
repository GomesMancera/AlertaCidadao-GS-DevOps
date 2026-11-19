package br.com.alerta.api.controller;

import br.com.alerta.api.dto.AlertaDtos;
import br.com.alerta.api.service.AlertaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    @PostMapping
    public AlertaDtos.Response create(@Valid @RequestBody AlertaDtos.CreateRequest request) {
        return alertaService.create(request);
    }

    @GetMapping
    public List<AlertaDtos.Response> list() {
        return alertaService.list();
    }

    @GetMapping("/{id}")
    public AlertaDtos.Response get(@PathVariable Long id) {
        return alertaService.get(id);
    }

    @PutMapping("/{id}")
    public AlertaDtos.Response update(@PathVariable Long id, @Valid @RequestBody AlertaDtos.UpdateRequest request) {
        return alertaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        alertaService.delete(id);
    }
}

package com.dutra.modeloCrud.controllers;

import com.dutra.modeloCrud.dtos.ClientEntry;
import com.dutra.modeloCrud.dtos.ClientResponse;
import com.dutra.modeloCrud.services.interfaces.ClientServiceInterface;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/client")
public class ClientController {

    private final ClientServiceInterface service;
    public ClientController(ClientServiceInterface service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ClientResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public Page<ClientResponse> findAll(Pageable pageable ) {
        return service.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> saveClient(@Valid @RequestBody ClientEntry newClient) {
        ClientResponse client = service.saveClient(newClient);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(client.id()).toUri();

        return ResponseEntity.created(uri).body(client);
    }
}

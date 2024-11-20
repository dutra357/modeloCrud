package com.dutra.modeloCrud.controllers;

import com.dutra.modeloCrud.dtos.ClientResponse;
import com.dutra.modeloCrud.services.interfaces.ClientServiceInterface;
import org.springframework.web.bind.annotation.*;

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
}

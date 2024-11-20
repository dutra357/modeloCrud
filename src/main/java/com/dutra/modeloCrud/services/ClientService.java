package com.dutra.modeloCrud.services;

import com.dutra.modeloCrud.dtos.ClientEntry;
import com.dutra.modeloCrud.dtos.ClientResponse;
import com.dutra.modeloCrud.repositories.ClientRepository;
import com.dutra.modeloCrud.services.interfaces.ClientServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements ClientServiceInterface {

    private final ClientRepository repository;
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public ClientResponse findById(Long id) {
        return null;
    }

    @Override
    public Page<ClientResponse> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public ClientResponse saveClient(ClientEntry newClient) {
        return null;
    }

    @Override
    public ClientResponse updateClient(ClientEntry clientUpdated) {
        return null;
    }

    @Override
    public void deleteClient(Long id) {

    }
}

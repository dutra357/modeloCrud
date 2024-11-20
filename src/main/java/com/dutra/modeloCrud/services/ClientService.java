package com.dutra.modeloCrud.services;

import com.dutra.modeloCrud.dtos.ClientEntry;
import com.dutra.modeloCrud.dtos.ClientResponse;
import com.dutra.modeloCrud.entities.Client;
import com.dutra.modeloCrud.repositories.ClientRepository;
import com.dutra.modeloCrud.services.interfaces.ClientServiceInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService implements ClientServiceInterface {

    private final ClientRepository repository;
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        return builderResponse(repository.findById(id).get());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(client -> builderResponse(client));
    }

    @Override
    @Transactional
    public ClientResponse saveClient(ClientEntry client) {
        Client newClient = repository.save(builderClient(client));
        return builderResponse(newClient);
    }

    @Override
    @Transactional
    public ClientResponse updateClient(Long id, ClientEntry clientUpdate) {
        Client clientUpdated = repository.getReferenceById(id);

        clientUpdated.setName(clientUpdate.name());
        clientUpdated.setCpf(clientUpdate.cpf());
        clientUpdated.setIncome(clientUpdate.income());
        clientUpdated.setBirthDate(clientUpdate.birthDate());
        clientUpdated.setBirthDate(clientUpdate.birthDate());

        repository.save(clientUpdated);
        return builderResponse(clientUpdated);
    }

    @Override
    public void deleteClient(Long id) {
        repository.deleteById(id);
    }

    private ClientResponse builderResponse(Client client) {
        return new ClientResponse(client.getId(),
                client.getName(), client.getCpf(),
                client.getIncome(), client.getBirthDate(),
                client.getChildren());
    }

    private Client builderClient(ClientEntry entry) {
        Client newClient = new Client();

        newClient.setName(entry.name());
        newClient.setCpf(entry.cpf());
        newClient.setIncome(entry.income());
        newClient.setBirthDate(entry.birthDate());
        newClient.setBirthDate(entry.birthDate());

        return newClient;
    }
}

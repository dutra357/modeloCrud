package com.dutra.modeloCrud.services;

import com.dutra.modeloCrud.dtos.ClientEntry;
import com.dutra.modeloCrud.dtos.ClientResponse;
import com.dutra.modeloCrud.entities.Client;
import com.dutra.modeloCrud.repositories.ClientRepository;
import com.dutra.modeloCrud.services.exceptions.DatabaseException;
import com.dutra.modeloCrud.services.exceptions.ResourceNotFoundException;
import com.dutra.modeloCrud.services.interfaces.ClientServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
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
        return builderResponse(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Client not found.")
        ));
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
        try {
            Client clientUpdated = repository.getReferenceById(id);

            clientUpdated.setName(clientUpdate.name());
            clientUpdated.setCpf(cpfFormatter(clientUpdate.cpf()));
            clientUpdated.setIncome(clientUpdate.income());
            clientUpdated.setBirthDate(clientUpdate.birthDate());
            clientUpdated.setBirthDate(clientUpdate.birthDate());

            repository.save(clientUpdated);
            return builderResponse(clientUpdated);
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("Client not found to update.");
        }
    }

    @Override
    public void deleteClient(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Client not found to delete.");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException exception) {
            throw new DatabaseException("Referential integrity violation (foreign key)");
        }
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
        newClient.setCpf(cpfFormatter(entry.cpf()));
        newClient.setIncome(entry.income());
        newClient.setBirthDate(entry.birthDate());
        newClient.setBirthDate(entry.birthDate());

        return newClient;
    }

    private String cpfFormatter(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) {
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos.");
        }
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}

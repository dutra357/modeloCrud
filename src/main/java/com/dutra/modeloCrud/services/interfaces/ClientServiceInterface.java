package com.dutra.modeloCrud.services.interfaces;

import com.dutra.modeloCrud.dtos.ClientEntry;
import com.dutra.modeloCrud.dtos.ClientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientServiceInterface {

    ClientResponse findById(Long id);
    Page<ClientResponse> findAll(Pageable pageable);
    ClientResponse saveClient(ClientEntry newClient);
    ClientResponse updateClient(ClientEntry clientUpdated);
    void deleteClient(Long id);
}

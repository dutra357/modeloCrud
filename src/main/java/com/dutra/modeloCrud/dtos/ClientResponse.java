package com.dutra.modeloCrud.dtos;

import java.time.LocalDate;

public record ClientResponse(
        Long id,
        String name,
        String cpf,
        Double income,
        LocalDate birthDate,
        Integer children
) {
}

package com.dutra.modeloCrud.dtos;

import java.time.LocalDate;

public record ClientEntry(
        String name,
        String cpf,
        Double income,
        LocalDate birthDate,
        Integer children
) {
}

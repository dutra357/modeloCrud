package com.dutra.modeloCrud.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClientEntry(

        @NotBlank(message = "Mandatory field.")
        String name,
        @Size(min = 11, max = 14, message = "Format: 000.000.000-00 or 00000000000. String between 11 or 14 characters.")
        @NotBlank(message = "Mandatory field.")
        String cpf,
        Double income,
        @PastOrPresent(message = "Field birthDate must be a past our present date.")
        LocalDate birthDate,
        @Positive
        Integer children
) {
}

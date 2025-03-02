package com.livia.crud_spring.enums.converters;

import java.util.stream.Stream;

import com.livia.crud_spring.enums.Status;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null)
            return null;
        return status.getValue();
    }

    @Override
    // Dado um valor, retornar o enum
    public Status convertToEntityAttribute(String value) {
        return Stream.of(Status.values())
                .filter(s -> s.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}

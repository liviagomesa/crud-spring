package com.livia.crud_spring.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    ACTIVE("Ativo"), INACTIVE("Inativo");

    private String value;

    @Override
    public String toString() {
        return value;
    }

}

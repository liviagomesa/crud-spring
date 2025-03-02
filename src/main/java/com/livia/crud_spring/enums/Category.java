package com.livia.crud_spring.enums;

public enum Category {
    FRONT_END("Front-end"), BACK_END("Back-end");

    // Propriedade do enum
    private String value;

    // Construtor
    private Category(String value) {
        this.value = value;
    }

    // Getter
    public String getValue() {
        return value;
    }

    // toString
    @Override
    public String toString() {
        return value;
    }

}

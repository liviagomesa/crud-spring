package com.livia.crud_spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Equivalente a @Getter @Setter @RequiredArgsConstructor @ToString
      // @EqualsAndHashCode
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id") // Utilizado para que essa propriedade seja
    // retornada no json com o nome "_id", como no Angular

    // @JsonIgnore // Se não quiser usar DTO para excluir essa propriedade no json
    // retornado
    private Long id;

    @Column(length = 200, nullable = false) // boa prática sempre colocar o tamanho da coluna para que não seja
                                            // reservado o espaço máximo a toa na memória
    private String name;

    @Column(length = 10, nullable = false)
    private String category;
}

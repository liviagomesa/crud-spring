package com.livia.crud_spring.model;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank // Não pode ter somente espaços, tem que ter outros caracteres
    @NotNull // Não deixa ser nulo nem vazio. Ver todas as opções de constraints em
             // https://jakarta.ee/specifications/bean-validation/3.0/apidocs/
    @Length(min = 5, max = 50)
    @Column(length = 50, nullable = false) // boa prática sempre colocar o tamanho da coluna para que não seja
                                           // reservado o espaço máximo a toa na memória
    private String name;

    @NotNull
    @Length(max = 10)
    @Pattern(regexp = "Back-end|Front-end") // Depois substituiremos por enum
    @Column(length = 10, nullable = false)
    private String category;
}

package com.livia.crud_spring.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
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

// Equivalente a @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Data
@Entity
// Anotação do hibernate para fazer remoção lógica (soft delete) sem precisar
// alterar todo o código do controller
@SQLDelete(sql = "UPDATE Course SET status = 'Inativo' WHERE id = ?")
// Anotação para adicionar essa cláusula em todo SELECT para nunca retornar
// linhas removidas
// Where(clause = "status = 'Ativo'") // Deprecated
@SQLRestriction(value = "status <> 'Inativo'")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // Utilizado para que essa propriedade seja retornada no json com o nome "_id",
    // como no Angular
    @JsonProperty("_id")

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

    @NotNull
    @Length(max = 10)
    @Pattern(regexp = "Ativo|Inativo") // Depois substituiremos por enum
    @Column(length = 10, nullable = false)
    private String status = "Ativo"; // Ao ser criado já vai como ativo
}

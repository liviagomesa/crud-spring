package com.livia.crud_spring.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.livia.crud_spring.enums.Category;
import com.livia.crud_spring.enums.Status;
import com.livia.crud_spring.enums.converters.CategoryConverter;
import com.livia.crud_spring.enums.converters.StatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

// Equivalente a @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode (toda entidade precisa ter um construtor vazio e setters para configurar os valores)
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
    @Column(length = 10, nullable = false)
    // Irá converter automaticamente o enum para string
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE; // Ao ser criado já vai como ativo
}

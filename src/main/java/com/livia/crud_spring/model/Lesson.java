package com.livia.crud_spring.model;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank // Não pode ter somente espaços, tem que ter outros caracteres
    @NotNull // Não deixa ser nulo nem vazio
    @Length(min = 5, max = 50)
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank // Não pode ter somente espaços, tem que ter outros caracteres
    @NotNull // Não deixa ser nulo nem vazio
    @Length(min = 10, max = 11)
    @Column(length = 11, nullable = false) // Não precisamos salvar o link inteiro, economizando espaço
    private String youtubeUrl;

    @NotNull
    // O fetch faz com que a aula carregue só quando chamar o curso; o optional
    // false impede essa coluna de ser vazia
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    // Necessário para evitar dependência circular
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Course course;
}

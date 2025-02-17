package com.livia.crud_spring.model;

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
    private Long id;

    @Column(length = 200, nullable = false) // boa prática sempre colocar o tamanho da coluna para que não seja
                                            // reservado o espaço máximo a toa na memória
    private String name;

    @Column(length = 10, nullable = false)
    private String category;
}

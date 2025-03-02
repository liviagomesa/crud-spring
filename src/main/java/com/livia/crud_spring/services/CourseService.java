package com.livia.crud_spring.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

// Model está encapsulado, ou seja, não é usado aqui também, apenas DTOs
import com.livia.crud_spring.dtos.CourseDTO;
import com.livia.crud_spring.dtos.mapper.CourseMapper;
import com.livia.crud_spring.exceptions.RecordNotFoundException;
import com.livia.crud_spring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Validated
@AllArgsConstructor // Para injetar o repository
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    // Por segurança NUNCA retornamos a entidade, apenas DTO
    public List<CourseDTO> list() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDTO) // é o mesmo que c -> courseMapper.toDTO(c)
                .toList();
    }

    // O id é do tipo Long (L maiúsculo), logo, é um objeto, logo, pode assumir
    // valor null
    public CourseDTO findById(@NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toDTO) // método map pode ser usado com stream e optional
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Não é necessário ter a propriedade id no objeto recebido no argumento porque
    // ela será gerada autoamticamente pelo banco de dados
    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO course) {
        return courseRepository.findById(id).map(c -> {
            c.setName(course.name());
            c.setCategory(courseMapper.convertCategoryValue(course.category()));
            return courseMapper.toDTO(courseRepository.save(c)); // O save retorna o curso atualizado
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));

    }

}

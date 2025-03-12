package com.livia.crud_spring.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

// Model está encapsulado, ou seja, não é usado aqui também, apenas DTOs
import com.livia.crud_spring.dtos.CourseDTO;
import com.livia.crud_spring.dtos.CoursePageDTO;
import com.livia.crud_spring.dtos.mapper.CourseMapper;
import com.livia.crud_spring.exceptions.RecordNotFoundException;
import com.livia.crud_spring.model.Course;
import com.livia.crud_spring.model.Lesson;
import com.livia.crud_spring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;

@Validated
@AllArgsConstructor // Para injetar o repository
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    // Por segurança NUNCA retornamos a entidade, apenas DTO
    // Repetimos as validações em todo o serviço para o caso de excluir no
    // controller, ter mais de um controller ou esquecer (é uma segurança extra)
    public CoursePageDTO list(@PositiveOrZero int pageNumber, @Positive @Max(100) int pageSize) {
        // O findAll recebe um pageable, que é construído com PageRequest.of. Em vez de
        // retornar numa List<Course>, retorna uma Page<Course>
        Page<Course> page = courseRepository.findAll(PageRequest.of(pageNumber, pageSize));
        List<CourseDTO> courses = page.getContent()
                .stream()
                .map(courseMapper::toDTO) // é o mesmo que c -> courseMapper.toDTO(c)
                .toList();
        return new CoursePageDTO(courses, page.getTotalPages(), page.getTotalElements());
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
    public CourseDTO create(@Valid @NotNull CourseDTO courseDTO) {
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(courseDTO)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO) {
        return courseRepository.findById(id).map(c -> {
            c.setName(courseDTO.name());
            c.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
            c.getLessons().clear();
            List<Lesson> updatedLessons = courseMapper.toEntity(courseDTO).getLessons(); // apenas para fazer o mapper
                                                                                         // de lessonDTO para lesson
            updatedLessons.forEach(l -> c.getLessons().add(l));
            return courseMapper.toDTO(courseRepository.save(c)); // O save retorna o curso atualizado
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));

    }

}

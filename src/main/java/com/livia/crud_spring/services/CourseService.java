package com.livia.crud_spring.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.livia.crud_spring.exceptions.RecordNotFoundException;
import com.livia.crud_spring.model.Course;
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

    public List<Course> list() {
        return courseRepository.findAll();
    }

    // O id é do tipo Long (L maiúsculo), logo, é um objeto, logo, pode assumir
    // valor null
    public Course findById(@NotNull @Positive Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    // Não é necessário ter a propriedade id no objeto recebido no argumento porque
    // ela será gerada autoamticamente pelo banco de dados
    public Course create(@Valid Course course) {
        return courseRepository.save(course);
    }

    public Course update(@NotNull @Positive Long id, @Valid Course course) {
        return courseRepository.findById(id).map(c -> {
            c.setName(course.getName());
            c.setCategory(course.getCategory());
            Course updatedCourse = courseRepository.save(c);
            return updatedCourse;
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id) {
        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));

    }

}

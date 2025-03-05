package com.livia.crud_spring.dtos.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.livia.crud_spring.dtos.CourseDTO;
import com.livia.crud_spring.dtos.LessonDTO;
import com.livia.crud_spring.enums.Category;
import com.livia.crud_spring.model.Course;

@Component
public class CourseMapper {
    public CourseDTO toDTO(Course course) {
        if (course == null)
            return null; // Um objeto sempre pode ser nulo
        List<LessonDTO> lessons = course.getLessons().stream()
                .map(l -> new LessonDTO(l.getId(), l.getName(), l.getYoutubeUrl()))
                .toList();
        // No DTO não temos enums, apenas Strings, pois são justamente o que o usuário
        // vê, por isso usamos getValue (getter do enum)
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(), lessons);
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null)
            return null; // Um objeto sempre pode ser nulo
        Course course = new Course();
        if (courseDTO.id() != null)
            course.setId(courseDTO.id());
        course.setName(courseDTO.name());
        course.setCategory(convertCategoryValue(courseDTO.category()));
        return course;
    }

    public Category convertCategoryValue(String value) {
        if (value == null)
            return null;
        return switch (value) {
            case "Front-end" -> Category.FRONT_END;
            case "Back-end" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
    }
}

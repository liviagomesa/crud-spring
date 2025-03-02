package com.livia.crud_spring.dtos.mapper;

import org.springframework.stereotype.Component;

import com.livia.crud_spring.dtos.CourseDTO;
import com.livia.crud_spring.model.Course;

@Component
public class CourseMapper {
    public CourseDTO toDTO(Course course) {
        if (course == null)
            return null; // Um objeto sempre pode ser nulo
        return new CourseDTO(course.getId(), course.getName(), course.getCategory());
    }

    public Course toEntity(CourseDTO courseDTO) {
        if (courseDTO == null)
            return null; // Um objeto sempre pode ser nulo
        Course course = new Course();
        if (courseDTO.id() != null)
            course.setId(courseDTO.id());
        course.setName(courseDTO.name());
        course.setCategory(courseDTO.category());
        return course;
    }
}

package com.livia.crud_spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livia.crud_spring.model.Course;
import com.livia.crud_spring.repository.CourseRepository;

@RestController
@RequestMapping("/api/courses") // Importante o "/api" para conseguirmos distinguir quais são os endpoints do
                                // Angular e do servidor
public class CourseController {

    private final CourseRepository courseRepository;

    // Recomendado fazer a injeção automática sempre via construtor em vez de via
    // Autowired, pois logo que o Spring for criar esse componente/bean, ele já vai
    // injetar essa dependência, em vez de fazer isso em um segundo momento (não é
    // muito importante, porém é preferencial). Opção: anotação AllArgsConstructor
    // do Lombok
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> list() {
        return courseRepository.findAll();
    }
}

package com.livia.crud_spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.livia.crud_spring.model.Course;
import com.livia.crud_spring.repository.CourseRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id) {
        return courseRepository.findById(id).map(c -> ResponseEntity.ok().body(c))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course) { // Não é necessário ter a propriedade id no
                                                                       // objeto recebido no
        // argumento porque ela será gerada autoamticamente pelo banco de
        // dados
        return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course course) {
        return courseRepository.findById(id).map(c -> {
            c.setName(course.getName());
            c.setCategory(course.getCategory());
            Course updatedCourse = courseRepository.save(c);
            return ResponseEntity.ok().body(updatedCourse);
        })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return courseRepository.findById(id).map(c -> {
            courseRepository.delete(c);
            return ResponseEntity.noContent().build();
        })
                .orElse(ResponseEntity.notFound().build());
    }

}

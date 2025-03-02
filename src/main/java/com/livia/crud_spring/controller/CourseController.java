package com.livia.crud_spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// O controller trabalha APENAS com DTO, só quem sabe do model é o serviço
import com.livia.crud_spring.dtos.CourseDTO;
import com.livia.crud_spring.services.CourseService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Recomendado fazer a injeção automática sempre via construtor em vez de via
// Autowired, pois logo que o Spring for criar esse componente/bean, ele já vai
// injetar essa dependência, em vez de fazer isso em um segundo momento (não é
// muito importante, porém é preferencial).
@AllArgsConstructor
@Validated // Necessário para funcionarem as validações de id (parâmetro) (anotações que
           // não são @Valid)
@RestController
@RequestMapping("/api/courses") // Importante o "/api" para conseguirmos distinguir quais são os endpoints do
                                // Angular e do servidor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public @ResponseBody List<CourseDTO> list() {
        return courseService.list();
    }

    @GetMapping("/{id}")
    // O id é do tipo Long (L maiúsculo), logo, é um objeto, logo, pode assumir
    // valor null
    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    // Não é necessário ter a propriedade id no objeto recebido no argumento porque
    // ela será gerada autoamticamente pelo banco de dados
    public CourseDTO create(@RequestBody @Valid @NotNull CourseDTO course) {
        return courseService.create(course);
    }

    @PutMapping("/{id}")
    public CourseDTO update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid @NotNull CourseDTO course) {
        return courseService.update(id, course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        courseService.delete(id);
    }

}

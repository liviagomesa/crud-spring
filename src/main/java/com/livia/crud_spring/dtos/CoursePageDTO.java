package com.livia.crud_spring.dtos;

import java.util.List;

public record CoursePageDTO(List<CourseDTO> courses, int totalPages, long totalElements) {

}

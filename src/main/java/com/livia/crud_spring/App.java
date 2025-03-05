package com.livia.crud_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.livia.crud_spring.enums.Category;
import com.livia.crud_spring.model.Course;
import com.livia.crud_spring.model.Lesson;
import com.livia.crud_spring.repository.CourseRepository;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean // temporário para exemplo
	CommandLineRunner initDatabase(CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll();

			Course c = new Course();
			c.setName("Angular com Spring");
			c.setCategory(Category.FRONT_END);

			Lesson l = new Lesson();
			l.setName("Introdução");
			l.setYoutubeUrl("youtube.com");
			// Necessário adicionar o curso à aula e a aula ao curso para ganhar desempenho
			// na aplicação (menos chamadas ao banco de dados)
			l.setCourse(c);
			c.getLessons().add(l);

			Lesson l2 = new Lesson();
			l2.setName("Desenvolvimento");
			l2.setYoutubeUrl("youtube.co2");
			l2.setCourse(c);
			c.getLessons().add(l2);

			courseRepository.save(c);
		};
	}

}

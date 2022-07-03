package com.example2.demo2.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student student1 = new Student(
                    "first student",
                    "first@email.com",
                    LocalDate.of(1999, Month.FEBRUARY, 2)
            );

            Student student2 = new Student(
                    "second student",
                    "second@email.com",
                    LocalDate.of(2002, Month.APRIL, 2)
            );

            repository.saveAll(
                    List.of(student1, student2)
            );
        };
    }
}
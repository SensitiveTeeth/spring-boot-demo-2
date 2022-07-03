package com.example2.demo2.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();


    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository
                .findStudentByEmail(student.getEmail());
        if (studentOptional.isPresent()) throw new IllegalStateException("email taken");
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean studentExist = studentRepository.existsById(studentId);
        if (!studentExist) throw new IllegalStateException("student with id " + studentId + " does not exist");
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudentNameAndEmail(Long studentId,
                                          String name,
                                          String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(
                        "student with id " + studentId + " does not exist"
                ));

        if (name == null ||
                name.length() < 1 ||
                Objects.equals(student.getName(), name)) throw new IllegalStateException("Invalid name");
        student.setName(name);
        if (email == null ||
                email.length() < 1 ||
                Objects.equals(student.getEmail(), name)) throw new IllegalStateException("Invalid email");
        Optional<Student> studentByEmailOptional = studentRepository.findStudentByEmail(email);
        if(studentByEmailOptional.isPresent()) throw new IllegalStateException("email taken");
        student.setEmail(email);
        studentRepository.save(student);
    }
}

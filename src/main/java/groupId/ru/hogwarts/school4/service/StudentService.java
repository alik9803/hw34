package groupId.ru.hogwarts.school4.service;

import groupId.ru.hogwarts.school4.model.Faculty;
import groupId.ru.hogwarts.school4.model.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(Long id);

    Student editStudent(Long id, Student student);

    void deleteStudent(Long id);

    void getAllStudents();

    Collection<Student> findStudentsByAgeBetween(int min, int max);

    Collection<Student> getStudentsByFaculty(Faculty faculty);

    Student createStudent(Student student);

    Student getById(Long studentId);

    Object getAllStudentAge(int anyInt);

    Object add(Student any);

    Object get(Long any);

    Object getStudentByAge(int age);

    Object getStudentsBetweenAge(int minAge, int maxAge);

    Object update(Long id, Student student);

    Object getFacultyOfStudent(Long any);

    Object getAllStudent();

}
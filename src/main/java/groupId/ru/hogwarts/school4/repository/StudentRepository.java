package groupId.ru.hogwarts.school4.repository;

import groupId.ru.hogwarts.school4.model.Faculty;
import groupId.ru.hogwarts.school4.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findByFaculty(Faculty faculty);
}
package groupId.ru.hogwarts.school4;

import groupId.ru.hogwarts.school4.controller.StudentController;
import groupId.ru.hogwarts.school4.model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerRestTemplateTest{
    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;


    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetStudents() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students", String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentById() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/{id}", Student.class, params));
    }

    @Test
    public void testGetStudentBuAgeBetween() {
        Map<String, String> params = new HashMap<>();
        params.put("ageMin", "12");
        params.put("ageMax", "16");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/byAgeBetween", Student.class, params));
    }

    @Test
    public void testGetFacultyOfStudent() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/getFacultyByID/{id}", Student.class, params));
    }

    @Test
    public void testGetStudentByAge() {
        Map<String, String> params = new HashMap<>();
        params.put("age", "12");

        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/students/by-age", Student.class, params));
    }

    @Test
    public void testPostStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Bob");

        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
                .isNotNull();
    }

    @Test
    public void testPutStudent() {
        Map<String, String> params = new HashMap<>();
        params.put("id", "1");
        Student student = new Student();
        student.setId(1L);
        student.setName("Bob");

        restTemplate.put("http://localhost:" + port + "/student", student, params);
    }

    @Test
    public void testDeleteStudent() {
        Map<String, String> params = new HashMap<>();

        restTemplate.put("http://localhost:" + port + "/student", params);
    }
}
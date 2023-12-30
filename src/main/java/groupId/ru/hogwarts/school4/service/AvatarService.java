package groupId.ru.hogwarts.school4.service;

import groupId.ru.hogwarts.school4.model.Avatar;
import groupId.ru.hogwarts.school4.model.Student;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {


    void upload(Long studentId, MultipartFile file) throws IOException;

    Avatar find(Long studentId);

    String saveFile(MultipartFile file, Student student) throws IOException;

}
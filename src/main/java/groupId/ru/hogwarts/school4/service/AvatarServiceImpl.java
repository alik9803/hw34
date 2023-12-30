package groupId.ru.hogwarts.school4.service;

import groupId.ru.hogwarts.school4.model.Avatar;
import groupId.ru.hogwarts.school4.model.Student;
import groupId.ru.hogwarts.school4.repository.AvatarRepository;
import groupId.ru.hogwarts.school4.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.Objects;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentRepository studentRepository,
                             AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    @Override
    public void upload(Long studentId, MultipartFile file) throws IOException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        String path = saveFile(file, student);

        Avatar avatar = avatarRepository.findByStudentId(studentId).orElse(new Avatar());
        avatar.setFilePath(path);
        avatar.setData(file.getBytes());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setStudent(student);

        avatarRepository.save(avatar);
    }

    public String saveFile(MultipartFile file, Student student) throws IOException {
        String originalFilename = Objects.requireNonNull(file.getOriginalFilename());
        int dotIndex = originalFilename.lastIndexOf('.');
        String ext = originalFilename.substring(dotIndex + 1);
        String path = avatarsDir + "/" + student.getId() + "_" + student.getName() + "." + ext;

        try (FileOutputStream out = new FileOutputStream(path)) {
            out.write(file.getBytes());
        }

        return path;
    }

    @Override
    public Avatar find(Long studentId) {
        return avatarRepository.findByStudentId(studentId).orElse(null);
    }
}
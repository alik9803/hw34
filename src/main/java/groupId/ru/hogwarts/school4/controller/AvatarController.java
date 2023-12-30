package groupId.ru.hogwarts.school4.controller;

import groupId.ru.hogwarts.school4.service.AvatarService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;

@RestController
@RequestMapping("/avatar")
public class AvatarController {

    public final AvatarService service;

    public AvatarController(AvatarService service) {
        this.service = service;
    }

    @PostMapping(value = "/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(@PathVariable Long studentId, @RequestParam MultipartFile files) throws IOException {
        service.upload(studentId, files);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<byte[]> find(@PathVariable Long studentId) {
        var avatar = service.find(studentId);
        if (avatar != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(avatar.getData().length);
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/file/{studentId}")
    public void findFile(@PathVariable Long studentId, HttpServletResponse response) throws IOException {
        var avatar = service.find(studentId);
        if (avatar == null) {
            response.setStatus(404);
            return;
        }
        File file = new File(avatar.getFilePath());
        if (!file.exists()) {
            response.setStatus(500);
            return;
        }
        response.setContentType(avatar.getMediaType());
        response.setContentLength((int) avatar.getFileSize());
        try (var out = response.getOutputStream();
             var in = new FileInputStream(avatar.getFilePath())) {
            in.transferTo(out);
        }
    }
}
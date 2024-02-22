package com.lisade.togeduck.controller;

import com.lisade.togeduck.service.S3FileUploadTestService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileUploadTestController {

    private final S3FileUploadTestService s3FileUploadTestService;

    @PostMapping
    public String uploadFile(@RequestPart("file") MultipartFile file)
        throws IOException, IOException {
        String url = s3FileUploadTestService.uploadFile(file);
        return url;
    }
}

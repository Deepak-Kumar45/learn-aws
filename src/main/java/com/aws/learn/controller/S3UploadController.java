package com.aws.learn.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aws.learn.service.S3Service;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/aws/learn/s3")
@RequiredArgsConstructor
public class S3UploadController {

    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadToS3(@RequestPart MultipartFile s3File) {
        try {
            this.s3Service.uploadToS3(s3File);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(s3File.getOriginalFilename()+" - File uploaded successfully");
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        byte[] downloadedFile = this.s3Service.downloadFile(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + fileName + "\"")
                .body(downloadedFile);
    }

}

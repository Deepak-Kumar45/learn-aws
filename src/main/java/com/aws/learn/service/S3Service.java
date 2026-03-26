package com.aws.learn.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.credentials.bucket}")
    private String bucketName;

    public String uploadToS3(MultipartFile file)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .contentDisposition("inline")
                .build(), RequestBody.fromBytes(file.getBytes()));
        return String.format("https://%s.s3.ap-south-1.amazonaws.com/%s", bucketName ,fileName);
    }

    public byte[] downloadFile(String key) {
        return this.s3Client.getObjectAsBytes(GetObjectRequest.builder().bucket(bucketName).key(key).build())
                .asByteArray();
    }
}

//S3service.java
package com.cloud;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.nio.file.Paths;

public class S3Service {
    private final S3Client s3;

    public S3Service(AwsCredentialsProvider credentialsProvider) {
        this.s3 = S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(credentialsProvider)
                .build();
    }

    public void listBuckets() {
        ListBucketsResponse response = s3.listBuckets();
        System.out.println("S3 Buckets:");
        response.buckets().forEach(b -> System.out.println(" - " + b.name()));
    }

    public void uploadFile(String bucket, String filePath, String key) {
        s3.putObject(PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build(),
                Paths.get(filePath));
        System.out.println("File uploaded: " + key);
    }

    public void downloadFile(String bucket, String key, String destinationPath) {
        s3.getObject(GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build(),
                Paths.get(destinationPath));
        System.out.println("File downloaded to: " + destinationPath);
    }
}


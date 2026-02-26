package com.cloud;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.file.Paths;

/**
 * S3Service class handles all operations related to AWS S3.
 * It provides functionality to:
 *  - List all S3 buckets
 *  - Upload a file to a bucket
 *  - Download a file from a bucket
 */
public class S3Service {

    // S3 client used to communicate with AWS S3 service
    private final S3Client s3;

    /**
     * Constructor initializes the S3 client
     * using the provided AWS credentials and region.
     *
     * @param credentialsProvider AWS credentials provider
     */
    public S3Service(AwsCredentialsProvider credentialsProvider) {

        // Build S3 client with specified region and credentials
        this.s3 = S3Client.builder()
                .region(Region.AP_SOUTH_1) // Asia Pacific (Mumbai) region
                .credentialsProvider(credentialsProvider)
                .build();
    }

    /**
     * Retrieves and prints all available S3 buckets
     * in the AWS account.
     */
    public void listBuckets() {

        // Send request to list all buckets
        ListBucketsResponse response = s3.listBuckets();

        System.out.println("S3 Buckets:");

        // Loop through each bucket and print its name
        response.buckets()
                .forEach(bucket -> System.out.println(" - " + bucket.name()));
    }

    /**
     * Uploads a file to a specified S3 bucket.
     *
     * @param bucket   Name of the S3 bucket
     * @param filePath Local file path of the file to upload
     * @param key      Object key (name) to assign in S3
     */
    public void uploadFile(String bucket, String filePath, String key) {

        // Build put object request with bucket name and key
        s3.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build(),
                Paths.get(filePath) // Local file path
        );

        // Confirmation message
        System.out.println("File uploaded: " + key);
    }

    /**
     * Downloads a file from a specified S3 bucket.
     *
     * @param bucket          Name of the S3 bucket
     * @param key             Object key (file name in S3)
     * @param destinationPath Local path where file will be saved
     */
    public void downloadFile(String bucket, String key, String destinationPath) {

        // Build get object request with bucket name and key
        s3.getObject(
                GetObjectRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .build(),
                Paths.get(destinationPath) // Local destination path
        );

        // Confirmation message
        System.out.println("File downloaded to: " + destinationPath);
    }
}

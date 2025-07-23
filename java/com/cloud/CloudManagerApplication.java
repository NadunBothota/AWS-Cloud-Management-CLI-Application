package com.cloud;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

import java.util.Scanner;

public class CloudManagerApplication {

    public static void main(String[] args) {
        System.out.println("Please provide your AWS credentials:");
        AwsCredentialsProvider credentialsProvider = AwsCredentialManager.promptForCredentials();

        EC2Service ec2Service = new EC2Service(credentialsProvider);
        S3Service s3Service = new S3Service(credentialsProvider);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== AWS Service Manager ===");
            System.out.println("1. EC2 Service");
            System.out.println("2. S3 Service");
            System.out.println("3. Exit");
            System.out.print("Select a service to manage: ");
            int serviceChoice = scanner.nextInt();
            scanner.nextLine();

            switch (serviceChoice) {
                case 1:
                    manageEC2(scanner, ec2Service);
                    break;
                case 2:
                    manageS3(scanner, s3Service);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void manageEC2(Scanner scanner, EC2Service ec2Service) {
        while (true) {
            System.out.println("\n--- EC2 Management ---");
            System.out.println("1. List EC2 Instances");
            System.out.println("2. Start EC2 Instance");
            System.out.println("3. Stop EC2 Instance");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int ec2choice = scanner.nextInt();
            scanner.nextLine();

            switch (ec2choice) {
                case 1 : ec2Service.listInstances();
                    break;
                case 2 : System.out.print("Enter EC2 Instance ID to start: ");
                    String startId = scanner.nextLine();
                    ec2Service.startInstance(startId);
                    break;
                case 3 : System.out.print("Enter EC2 Instance ID to stop: ");
                    String stopId = scanner.nextLine();
                    ec2Service.stopInstance(stopId);
                    break;
                case 4 :  return;
                default : System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void manageS3(Scanner scanner, S3Service s3Service) {
        while (true) {
            System.out.println("\n--- S3 Management ---");
            System.out.println("1. List S3 Buckets");
            System.out.println("2. Upload File to S3 Bucket");
            System.out.println("3. Download File from S3 Bucket");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int s3choice = scanner.nextInt();
            scanner.nextLine();

            switch (s3choice) {
                case 1 : s3Service.listBuckets();
                         break;
                case 2 :
                    System.out.print("Enter Bucket Name: ");
                    String bucketUpload = scanner.nextLine();
                    System.out.print("Enter File Path to Upload: ");
                    String filepath = scanner.nextLine();
                    System.out.print("Enter Key (Object name in S3): ");
                    String keyName = scanner.nextLine();
                    s3Service.uploadFile(bucketUpload, filepath, keyName);
                    break;
                case 3 :
                    System.out.print("Enter Bucket Name: ");
                    String bucketDownload = scanner.nextLine();
                    System.out.print("Enter Key: ");
                    String keyToDownload = scanner.nextLine();
                    System.out.print("Enter Destination Path: ");
                    String destPath = scanner.nextLine();
                    s3Service.downloadFile(bucketDownload, keyToDownload, destPath);
                    break;
                case 4 : return;
                default : System.out.println("Invalid option. Try again.");
            }
        }
    }
}

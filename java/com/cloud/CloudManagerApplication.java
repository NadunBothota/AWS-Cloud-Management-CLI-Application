package com.cloud;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import java.util.Scanner;

/**
 * Main application class for managing AWS EC2 and S3 services.
 * This program allows users to interact with AWS resources
 * through a console-based menu system.
 */
public class CloudManagerApplication {

    public static void main(String[] args) {

        // Prompt user to enter AWS credentials
        System.out.println("Please provide your AWS credentials:");
        AwsCredentialsProvider credentialsProvider = AwsCredentialManager.promptForCredentials();

        // Initialize EC2 and S3 service classes using provided credentials
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
            scanner.nextLine(); // Clear newline from buffer

            // Switch based on user's main menu selection
            switch (serviceChoice) {
                case 1:
                    manageEC2(scanner, ec2Service); // Navigate to EC2 management
                    break;

                case 2:
                    manageS3(scanner, s3Service); // Navigate to S3 management
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    System.exit(0); // Exit application
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    /**
     * Handles EC2-related operations such as
     * listing, starting, and stopping instances.
     */
    private static void manageEC2(Scanner scanner, EC2Service ec2Service) {

        // EC2 submenu loop
        while (true) {
            System.out.println("\n--- EC2 Management ---");
            System.out.println("1. List EC2 Instances");
            System.out.println("2. Start EC2 Instance");
            System.out.println("3. Stop EC2 Instance");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int ec2choice = scanner.nextInt();
            scanner.nextLine(); // Clear newline

            switch (ec2choice) {

                // List all EC2 instances
                case 1:
                    ec2Service.listInstances();
                    break;

                // Start a specific EC2 instance
                case 2:
                    System.out.print("Enter EC2 Instance ID to start: ");
                    String startId = scanner.nextLine();
                    ec2Service.startInstance(startId);
                    break;

                // Stop a specific EC2 instance
                case 3:
                    System.out.print("Enter EC2 Instance ID to stop: ");
                    String stopId = scanner.nextLine();
                    ec2Service.stopInstance(stopId);
                    break;

                // Return to main menu
                case 4:
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Handles S3-related operations such as
     * listing buckets, uploading files, and downloading files.
     */
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

                // List all S3 buckets
                case 1:
                    s3Service.listBuckets();
                    break;

                // Upload a file to an S3 bucket
                case 2:
                    System.out.print("Enter Bucket Name: ");
                    String bucketUpload = scanner.nextLine();

                    System.out.print("Enter File Path to Upload: ");
                    String filepath = scanner.nextLine();

                    System.out.print("Enter Key (Object name in S3): ");
                    String keyName = scanner.nextLine();

                    s3Service.uploadFile(bucketUpload, filepath, keyName);
                    break;

                // Download a file from an S3 bucket
                case 3:
                    System.out.print("Enter Bucket Name: ");
                    String bucketDownload = scanner.nextLine();

                    System.out.print("Enter Key: ");
                    String keyToDownload = scanner.nextLine();

                    System.out.print("Enter Destination Path: ");
                    String destPath = scanner.nextLine();

                    s3Service.downloadFile(bucketDownload, keyToDownload, destPath);
                    break;

                // Return to main menu
                case 4:
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}



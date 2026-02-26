// AwsCredentialManager.java

package com.cloud;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityRequest;

import java.util.Scanner;

/**
 * AwsCredentialManager handles:
 *  - Prompting the user to enter AWS credentials
 *  - Creating a credentials provider
 *  - Validating the credentials using AWS STS service
 */
public class AwsCredentialManager {

    /**
     * Prompts the user to enter AWS Access Key and Secret Key.
     * Creates a credentials provider and validates it.
     *
     * @return AwsCredentialsProvider if credentials are valid
     */
    public static AwsCredentialsProvider promptForCredentials() {

        Scanner scanner = new Scanner(System.in);

        // Ask user to input AWS Access Key ID
        System.out.print("Enter AWS Access Key ID: ");
        String accessKeyId = scanner.nextLine();

        // Ask user to input AWS Secret Access Key
        System.out.print("Enter AWS Secret Access Key: ");
        String secretAccessKey = scanner.nextLine();

        // Create basic AWS credentials using provided keys
        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(accessKeyId, secretAccessKey);

        // Wrap credentials inside a static credentials provider
        AwsCredentialsProvider provider =
                StaticCredentialsProvider.create(credentials);

        // Validate credentials before returning
        if (!validateCredentials(provider)) {
            System.out.println(" Invalid credentials. Exiting.");
            System.exit(1); // Stop application if credentials are invalid
        }

        System.out.println("AWS Credentials validated.");
        return provider;
    }

    /**
     * Validates AWS credentials by calling AWS STS (Security Token Service).
     * If credentials are valid, STS returns caller identity.
     *
     * @param provider AWS credentials provider
     * @return true if credentials are valid, false otherwise
     */
    private static boolean validateCredentials(AwsCredentialsProvider provider) {

        try {
            // Build STS client using provided credentials
            StsClient stsClient = StsClient.builder()
                    .region(Region.AP_SOUTH_1) // Asia Pacific (Mumbai) region
                    .credentialsProvider(provider)
                    .build();

            // Call AWS STS to verify identity
            stsClient.getCallerIdentity(
                    GetCallerIdentityRequest.builder().build()
            );

            // If no exception occurs, credentials are valid
            return true;

        } catch (Exception e) {
            // If exception occurs, credentials are invalid
            return false;
        }
    }
}

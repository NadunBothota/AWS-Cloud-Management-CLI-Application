//CredentialManager.java
package com.cloud;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.GetCallerIdentityRequest;

import java.util.Scanner;

public class AwsCredentialManager {

    public static AwsCredentialsProvider promptForCredentials() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter AWS Access Key ID: ");
        String accessKeyId = scanner.nextLine();
        System.out.print("Enter AWS Secret Access Key: ");
        String secretAccessKey = scanner.nextLine();

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        AwsCredentialsProvider provider = StaticCredentialsProvider.create(credentials);

        // Validate credentials
        if (!validateCredentials(provider)) {
            System.out.println(" Invalid credentials. Exiting.");
            System.exit(1);
        }

        System.out.println("AWS Credentials validated.");
        return provider;
    }

    private static boolean validateCredentials(AwsCredentialsProvider provider) {
        try {
            StsClient stsClient = StsClient.builder()
                    .region(Region.AP_SOUTH_1)
                    .credentialsProvider(provider)
                    .build();
            stsClient.getCallerIdentity(GetCallerIdentityRequest.builder().build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}



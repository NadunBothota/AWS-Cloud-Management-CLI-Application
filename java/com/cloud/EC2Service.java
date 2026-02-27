// EC2Service.java

package com.cloud;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

/**
 * EC2Service class handles all EC2-related operations such as:
 * - Listing EC2 instances
 * - Starting an EC2 instance
 * - Stopping an EC2 instance
 */
public class EC2Service {

    // EC2 client object used to communicate with AWS EC2
    private final Ec2Client ec2;

    /**
     * Constructor initializes the EC2 client with:
     * - AWS credentials provided by the user
     * - Specific AWS region (AP_SOUTH_1 - Mumbai region)
     */
    public EC2Service(AwsCredentialsProvider credentialsProvider) {
        this.ec2 = Ec2Client.builder()
                .region(Region.AP_SOUTH_1)  // Set AWS region
                .credentialsProvider(credentialsProvider)  // Set credentials
                .build();
    }

    /**
     * Lists all EC2 instances in the configured AWS region.
     * It retrieves instance details and prints:
     * - Instance ID
     * - Current instance state (running, stopped, etc.)
     */
    public void listInstances() {

        // Send request to describe all EC2 instances
        DescribeInstancesResponse response = ec2.describeInstances();

        // Loop through each reservation (group of instances)
        for (Reservation res : response.reservations()) {

            // Loop through each instance inside reservation
            for (Instance inst : res.instances()) {

                // Print instance ID and current state
                System.out.printf("Instance ID: %s | State: %s%n",
                        inst.instanceId(),
                        inst.state().nameAsString());
            }
        }
    }

    /**
     * Starts an EC2 instance using its instance ID.
     *
     * @param instanceId The ID of the EC2 instance to start
     */
    public void startInstance(String instanceId) {

        // Build and send start request to AWS
        ec2.startInstances(StartInstancesRequest.builder()
                .instanceIds(instanceId)
                .build());

        // Confirmation message
        System.out.println("Instance started: " + instanceId);
    }

    /**
     * Stops an EC2 instance using its instance ID.
     *
     * @param instanceId The ID of the EC2 instance to stop
     */
    public void stopInstance(String instanceId) {

        // Build and send stop request to AWS
        ec2.stopInstances(StopInstancesRequest.builder()
                .instanceIds(instanceId)
                .build());

        // Confirmation message
        System.out.println("Instance stopped: " + instanceId);
    }
}


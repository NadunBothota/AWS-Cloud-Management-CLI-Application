package com.cloud;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

public class EC2Service {
    private final Ec2Client ec2;

    public EC2Service(AwsCredentialsProvider credentialsProvider) {
        this.ec2 = Ec2Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(credentialsProvider)
                .build();
    }

    public void listInstances() {
        DescribeInstancesResponse response = ec2.describeInstances();
        for (Reservation res : response.reservations()) {
            for (Instance inst : res.instances()) {
                System.out.printf("Instance ID: %s | State: %s%n",
                        inst.instanceId(), inst.state().nameAsString());
            }
        }
    }

    public void startInstance(String instanceId) {
        ec2.startInstances(StartInstancesRequest.builder()
                .instanceIds(instanceId)
                .build());
        System.out.println("Instance started: " + instanceId);
    }

    public void stopInstance(String instanceId) {
        ec2.stopInstances(StopInstancesRequest.builder()
                .instanceIds(instanceId)
                .build());
        System.out.println("Instance stopped: " + instanceId);
    }
}

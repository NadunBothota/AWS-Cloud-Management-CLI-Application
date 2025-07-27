package com.cloud;

import static org.junit.jupiter.api.Assertions.*;

public class EC2Test {

    public String testInstanceStartCommand(String s) {
        EC2Test manager = new EC2Test();
        String response = manager.testInstanceStartCommand("i-12345678"); // mock this if it uses real AWS
        assertNotNull(response);
        return response;
    }
}

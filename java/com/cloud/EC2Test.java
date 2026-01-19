//EC2Test.java
package com.cloud;

import static org.junit.jupiter.api.Assertions.*;

public class EC2Test {

    public String testInstanceStartCommand(String s) {
        EC2Test manager = new EC2Test();
        String response = manager.testInstanceStartCommand("i-12345678");
        assertNotNull(response);
        return response;
    }
}



package net.isger.brick;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BrickVelocityTest extends TestCase {

    public BrickVelocityTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(BrickVelocityTest.class);
    }

    public void testVelocity() {
        assertTrue(true);
    }

}

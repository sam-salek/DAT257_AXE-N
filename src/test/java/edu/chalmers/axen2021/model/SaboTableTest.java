package edu.chalmers.axen2021.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for SaboTable class. 
 * @author Tilda Grönlund
 * @author Sara Vardheim
 */

class SaboTableTest {
    private final SaboTable sabo = new SaboTable();

    /**
     * Tests the getRE(String) method in SaboTable. Tries to get RE for two valid and
     * one invalid apartment type.
     */
    @Test
    public void getRETest() {
        assertEquals(sabo.getRE("1rok"), 34.0);   // Valid input
        assertEquals(sabo.getRE("3rok"), 44.0);   // Valid input
        assertThrows(NullPointerException.class, () -> sabo.getRE("10rok"));  // Invalid input, should throw NullPointerException
    }
}

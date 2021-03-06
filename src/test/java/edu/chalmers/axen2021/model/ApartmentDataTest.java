package edu.chalmers.axen2021.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ApartmentData class.
 * @author Sara Vardheim
 * @author Ahmad Al-Aref
 * @author Tilda Grönlund
 */
class ApartmentDataTest {
    private final ApartmentData apt = new ApartmentData();

    /**
     * Tests the getMonthlyRent(String, int, double) method in ApartmentData class for low
     * and high monthly rent, respectively.
     */
    @Test
    public void getMonthlyRentTest() {
        assertEquals(apt.getMonthlyRent("1rok", 1350, 29.0), 4510, 0.35); // Low
        assertEquals(apt.getMonthlyRent("1rok", 1700, 29.0), 5680,0.7); // High
    }

    /**
     * Tests the getKrPerSqm(String, int, double) method in ApartmentData class
     * for low and high monthly rent, respectively.
     */
    @Test
    public void getKrPerSqmTest() {
        assertEquals(apt.getKrPerSqm("1rok", 1350, 29.0 ), 1866, 0.35); // Low
        assertEquals(apt.getKrPerSqm("1rok", 1700, 29.0), 2350,0.35); // High
    }

    /**
     * Tests the getSubsidy(double, int, double) method in ApartmentData class for apartments
     * under 35 sqm, between 35 and 70 sqm and over 70 sqm.
     */
    @Test
    public void getSubsidyTest() {
        assertEquals(apt.getSubsidy(4800, 24, 29.0), 5846.4); // 1rok, BOA 29.0
        assertEquals(apt.getSubsidy(4800, 36, 48.0), 12549.6); // 2rok, BOA 48.0
        assertEquals(apt.getSubsidy(4800, 1, 87.0), 441); // 4rok, BOA 87.0
    }
}

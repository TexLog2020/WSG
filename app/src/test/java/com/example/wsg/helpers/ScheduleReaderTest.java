package com.example.wsg.helpers;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Testing για την κλάση  {@link ScheduleReader}
 */
public class ScheduleReaderTest {

    private static final String MONDAY_MORNING_EMPLOYEES = "John Nickou,Ballentina Sarakou";
    private static Map<String, Map<String, Map<String, String>>> map;

    private ScheduleReader sr;
   //Εκτελείται μια φορά
    @BeforeClass
    public static void init() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("src/test/resources/wsg/week1.json"));
        map = new Gson().fromJson(br, Map.class);
    }
    //Εκτελείται πριν από κάθε test μεμονωμένα
    @Before
    public void setUp() {
        sr = new ScheduleReader(map);
    }

    @Test
    public void testConstructor() {
        Assert.assertNotNull(sr);
    }

    @Test
    public void has5Days() {
        assertEquals(5, sr.getWorkingEmployees().size());
    }

    @Test
    public void MondayMorningEmployees() {
        assertEquals(MONDAY_MORNING_EMPLOYEES, sr.getWorkingEmployees().get(0).getMorningShift());
    }

    //Εκτελείται μετά από κάθε ολοκλήρωση ενός test
    @After
    public void tearDown() {
        sr = null;
    }


}
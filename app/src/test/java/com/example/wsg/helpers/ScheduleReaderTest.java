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


public class ScheduleReaderTest {

    private static final String MONDAY_MORNING_EMPLOYEES = "John Nickou,Ballentina Sarakou";
    private static Map<String, Map<String, Map<String, String>>> map;

    private ScheduleReader sr;

    @BeforeClass
    public static void init() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader("src/test/resources/wsg/week1.json"));
        map = new Gson().fromJson(br, Map.class);
    }

    @Before
    public void setUp() {
        sr = new ScheduleReader(map);
    }








    @After
    public void tearDown() {
        sr = null;
    }


}
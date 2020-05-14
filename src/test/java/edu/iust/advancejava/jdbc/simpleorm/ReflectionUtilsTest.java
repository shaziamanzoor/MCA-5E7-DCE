package edu.iust.advancejava.jdbc.simpleorm;

import edu.iust.advancejava.jdbc.Vehicle;
import edu.iust.advancejava.jdbc.simpleorm.FieldInfo;
import edu.iust.advancejava.jdbc.simpleorm.ReflectionUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ReflectionUtilsTest {

    @Test
    public void testClassName() throws Exception {
        assertEquals("ReflectionUtils", ReflectionUtils.className(ReflectionUtils.class));
        assertEquals("Vehicle", ReflectionUtils.className(Vehicle.class));
    }

    @Test
    public void testGetFieldNames() throws Exception {
        Set<String> expected = new HashSet<>(
                Arrays.asList("id", "registrationNumber", "numberOfWheels", "dateOfRegistration"));
        Set<String> actual = new HashSet<>(ReflectionUtils.getFieldNames(Vehicle.class)
                .stream().map(FieldInfo::getFieldName).collect(Collectors.toList()));

        assertEquals(expected, actual);
    }
    @Test
    public void testMakeFirstCharLower() throws Exception {
        assertEquals("shazia",ReflectionUtils.makeFirstCharLower("Shazia"));
    }
}
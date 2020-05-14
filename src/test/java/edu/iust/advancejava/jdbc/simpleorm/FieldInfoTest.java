package edu.iust.advancejava.jdbc.simpleorm;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class FieldInfoTest {

    @Test
    public void testGetter() throws InvocationTargetException, IllegalAccessException {
        List<FieldInfo> fields = ReflectionUtils.getFieldNames(Car.class);
        Optional<FieldInfo> nameField = fields.stream().filter(f -> f.getFieldName().equals("name")).findFirst();
        assertTrue(nameField.isPresent());
        FieldInfo field = nameField.get();
        Car car = new Car();
        car.setName("Maruti");
        assertEquals(car.getName(), field.getValue(car));
    }

    @Test
    public void testSetter() throws InvocationTargetException, IllegalAccessException {
        List<FieldInfo> fields = ReflectionUtils.getFieldNames(Car.class);
        Optional<FieldInfo> nameField = fields.stream().filter(f -> f.getFieldName().equals("name")).findFirst();
        assertTrue(nameField.isPresent());
        FieldInfo field = nameField.get();
        Car car = new Car();
        field.setValue(car, "Honda");
        assertEquals(car.getName(), field.getValue(car));
    }
}
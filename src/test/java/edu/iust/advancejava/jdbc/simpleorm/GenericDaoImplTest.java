package edu.iust.advancejava.jdbc.simpleorm;

import edu.iust.advancejava.jdbc.connection.ConnectionManager;
import edu.iust.advancejava.jdbc.connection.ConnectionManagerImpl;
import org.junit.Test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GenericDaoImplTest {

    @Test
    public void testCreate() throws Exception {
        ConnectionManager manager = new ConnectionManagerImpl();
        manager.initialize();
        try (Connection conn = manager.getConnection()){
            GenericDao<Car> dao = new GenericDaoImpl<>(conn, Car.class);
            Car car = new Car();
            car.setId(2);
            car.setName("Honda");
            car.setDateOfRelease(LocalDate.now());
            car.setMaximumSpeed(150);

            // Create an instance.
            dao.create(car);

            Optional<Car> r = dao.get(car.getId());
            assertTrue(r.isPresent());
            assertEquals("Honda", r.get().getName());

            // Update
            car.setName("New Honda");
            dao.update(car);

            r = dao.get(car.getId());
            assertTrue(r.isPresent());
            assertEquals("New Honda", r.get().getName());

            // Delete
            dao.delete(car);

            r = dao.get(car.getId());
            assertFalse(r.isPresent());
        }

    }


}
package edu.iust.advancejava.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class VehicleJDBC {
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String connectionString = "jdbc:mysql://localhost:3306/iustdemo";
        String username = "root";
        String password = "mysqlpassword";
        try (Connection conn = DriverManager.getConnection(connectionString, username, password)) {
            VehicleDao dao = new VehicleDao(conn);
            runMysqlCommands(dao);
        }
    }

    private static void runMysqlCommands(VehicleDao dao) throws SQLException {
        Vehicle vehicle = new Vehicle("JKB00123", 2, LocalDate.now());
        int id = dao.create(vehicle);

        Optional<Vehicle> returned = dao.getById(id);
        System.out.println(returned);

        vehicle.setRegistrationNumber("DELHI191");
        dao.update(vehicle);
        returned = dao.getById(id);

        System.out.println("After Updation result is ");
        System.out.println(returned);

        dao.delete(vehicle);
        returned = dao.getById(id);
        if (!returned.isPresent())
            System.out.println("The vehicle has been succesfully deleted from the database");
    }
}



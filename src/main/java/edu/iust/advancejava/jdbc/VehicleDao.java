package edu.iust.advancejava.jdbc;

import java.sql.*;
import java.util.Optional;

public class VehicleDao {private final Connection conn;

    public VehicleDao(Connection conn){
        this.conn = conn;
    }

    public int create(Vehicle vehicle) throws SQLException {
        String sql = "insert into Vehicle(registration_number, no_of_wheels, date_of_registration)values(?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, vehicle.getRegistrationNumber());
            statement.setInt(2, vehicle.getNumberOfWheels());
            statement.setDate(3, java.sql.Date.valueOf(vehicle.getDateOfRegistration()));
            statement.executeUpdate();
            try(ResultSet rs = statement.getGeneratedKeys()){
                rs.next();
                vehicle.setId(rs.getInt(1));
                return vehicle.getId();
            }
        }
    }

    public void update(Vehicle vehicle) throws SQLException {
        String sql = "update vehicle set registration_number=?, no_of_wheels=?, date_of_registration=? where id = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, vehicle.getRegistrationNumber());
            statement.setInt(2, vehicle.getNumberOfWheels());
            statement.setDate(3, java.sql.Date.valueOf(vehicle.getDateOfRegistration()));
            statement.setInt(4, vehicle.getId());
            statement.execute();
        }
    }

    public void delete(Vehicle vehicle) throws SQLException {
        String sql = "delete from vehicle where id = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1, vehicle.getId());
            statement.execute();
        }
    }

    public Optional<Vehicle> getById(int id) throws SQLException {
        String sql = "select id, registration_number, no_of_wheels, date_of_registration from vehicle where id = ?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()){
                if (rs.next()){
                    return Optional.of(
                            new Vehicle(
                                    rs.getString("registration_number"),
                                    rs.getInt("no_of_wheels"),
                                    rs.getDate("date_of_registration").toLocalDate()));
                }

                return  Optional.empty();
            }
        }
    }

}

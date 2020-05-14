package edu.iust.advancejava.jdbc;

import java.sql.*;
import java.util.Optional;

public class HelloJDBC {

    public static class Hello {
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        private String message;
        private int id;

        public Hello(int id, String message){
            this.id = id;
            this.message = message;
        }

        @Override
        public String toString() {
            return "Hello{" +
                    "message = '" + message + '\'' +
                    ", id = " + id +
                    '}';
        }
    }

    public static void main(String [] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String connectionString = "jdbc:mysql://localhost:3306/iustdemo";
        String username = "root";
        String password = "mysqlpassword";
        try (Connection conn = DriverManager.getConnection(connectionString, username, password)){
            runMySqlCommands(conn);
        }
    }

    private static void runMySqlCommands(Connection conn) throws SQLException {
        Optional<Integer> id = createMessage("Hello Advanced Java", conn);
        if (id.isPresent()){
            updateMessage(id.get(), "Welcome to Advanced java JDBC class", conn);
            Optional<Hello> hello = getHello(id.get(), conn);
            hello.ifPresent(System.out::println);
        }
    }

    private static Optional<Hello> getHello(Integer id, Connection conn) throws SQLException {
        String sql = "select id, message from hello where id=?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()){
                return rs.next() ? Optional.of(new Hello(rs.getInt(1), rs.getString(2)))
                        : Optional.empty();
            }
        }
    }

    private static void updateMessage(Integer id, String message, Connection conn) throws SQLException {
        String sql = "update hello set message=? where id=?";
        try(PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, message);
            statement.setInt(2, id);
            statement.execute();
        }
    }

    private static Optional<Integer> createMessage(String message, Connection conn) throws SQLException {
        String sql = "insert into hello(message) values(?)";
        try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, message);
            statement.execute();
            try(ResultSet rs = statement.getGeneratedKeys()) {
                return rs.next() ? Optional.of(rs.getInt(1)) : Optional.empty();
            }
        }
    }
}


















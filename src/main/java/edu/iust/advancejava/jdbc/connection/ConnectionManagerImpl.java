package edu.iust.advancejava.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionManagerImpl implements ConnectionManager {

    private Properties properties = new Properties();
    private List<Connection> connections = new ArrayList<>();

    @Override
    public void initialize() throws Exception {
        properties.load(ConnectionManagerImpl.class.getResourceAsStream("/Connection.properties"));
        Class.forName(properties.getProperty("driverClassName"));
    }

    @Override
    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(
                properties.getProperty("connectionString"),
                properties.getProperty("username"),
                properties.getProperty("password"));
        connections.add(conn);
        return conn;
    }

    @Override
    public void close() {
        for(Connection connection: connections){
            try {
                connection.close();
            } catch (SQLException e) {
                //ignore
            }
        }
    }
}

package edu.iust.advancejava.jdbc.connection;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionManager extends Closeable {
    void initialize() throws Exception;
    Connection getConnection() throws SQLException;
    void close();
}

package edu.iust.advancejava.jdbc.simpleorm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Optional;

public interface GenericDao<T extends IEntity> {
    void create(T entity) throws SQLException, InvocationTargetException, IllegalAccessException;
    void update(T entity) throws SQLException, InvocationTargetException, IllegalAccessException;
    void delete(T entity) throws SQLException, InvocationTargetException, IllegalAccessException;
    Optional<T> get(int id) throws SQLException, InvocationTargetException, IllegalAccessException, InstantiationException;
}

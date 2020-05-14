package edu.iust.advancejava.jdbc.simpleorm;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class GenericDaoImpl<T extends IEntity> implements GenericDao<T> {

    private final Connection conn;
    private final Class<?> entityClass;
    private final List<FieldInfo> fields;

    public GenericDaoImpl(Connection conn, Class<?> entityClass){
        this.conn = conn;
        this.entityClass = entityClass;
        this.fields = ReflectionUtils.getFieldNames(entityClass);
    }

    @Override
    public void create(T entity) throws SQLException, InvocationTargetException, IllegalAccessException {
        assert entity != null;
        String query = createInsertQuery();
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            setValues(stmt, entity);
            stmt.execute();
        }
    }

    @Override
    public void update(T entity) throws SQLException, InvocationTargetException, IllegalAccessException {
        String query = createUpdateQuery();
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            setValues(stmt, entity);
            stmt.setInt(fields.size() + 1, entity.getId());
            stmt.execute();
        }
    }

    @Override
    public void delete(T entity) throws SQLException, InvocationTargetException, IllegalAccessException {
        String query = String.format("delete from %s where id = ?", tableName());
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, entity.getId());
            stmt.execute();
        }
    }

    @Override
    public Optional<T> get(int id) throws SQLException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String query = String.format("select %s from %s where id = ?", commaSeparatedFieldNames(), tableName());
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    @SuppressWarnings("unchecked")
                    T entity = (T)entityClass.newInstance();
                    getValues(rs, entity);
                    return Optional.of(entity);
                } else {
                    return Optional.empty();
                }

            }
        }

    }

    protected void setValues(PreparedStatement stmt, T entity) throws InvocationTargetException, IllegalAccessException, SQLException {
        int i = 1;

        for(FieldInfo field: fields){
            Object value = field.getValue(entity);

            if(field.getDataType() == String.class){
                stmt.setString(i, (String)value);
            } else if(field.getDataType() == Integer.class || field.getDataType() == int.class){
                stmt.setInt(i, (Integer)value);
            }else if(field.getDataType() == Double.class || field.getDataType() == double.class){
                stmt.setDouble(i, (Double)value);
            } else if(field.getDataType() == Long.class || field.getDataType() == long.class){
                stmt.setLong(i, (Long)value);
            } else if(field.getDataType() == Float.class || field.getDataType() == float.class){
                stmt.setFloat(i, (Float)value);
            } else if(field.getDataType() == LocalDate.class){
                stmt.setDate(i, Date.valueOf((LocalDate)value));
            } else {
                throw new RuntimeException("Data Type not mapped : " + field.getDataType());
            }

            i = i + 1;
        }
    }

    protected void getValues(ResultSet rs, T entity) throws InvocationTargetException, IllegalAccessException, SQLException {
        int i = 1;

        for(FieldInfo field: fields){
            Object value;
            if(field.getDataType() == String.class){
                value = rs.getString(i);
            } else if(field.getDataType() == Integer.class || field.getDataType() == int.class){
                value = rs.getInt(i);
            }else if(field.getDataType() == Double.class || field.getDataType() == double.class){
                value = rs.getDouble(i);
            } else if(field.getDataType() == Long.class || field.getDataType() == long.class){
                value = rs.getLong(i);
            } else if(field.getDataType() == Float.class || field.getDataType() == float.class){
                value = rs.getFloat(i);
            } else if(field.getDataType() == LocalDate.class){
                value = rs.getDate(i).toLocalDate();
            } else {
                throw new RuntimeException("Data Type not mapped : " + field.getDataType());
            }
            field.setValue(entity, value);
            i = i + 1;
        }
    }

    private String createInsertQuery() {
        return String.format("insert into %s(%s) values(%s)",
                tableName(), commaSeparatedFieldNames(), commaSeparatedQuestionMarks());
    }

    private String createUpdateQuery(){
        return String.format("update %s set %s where id = ?", tableName(),
                commaSeparatedFieldNamesWithQuestionMarks());
    }

    private String commaSeparatedFieldNamesWithQuestionMarks() {
        return fields.stream().map(f -> f.getFieldName() + "=?").reduce(GenericDaoImpl::combineWithComma).orElse("");
    }

    private String commaSeparatedQuestionMarks() {
        return fields.stream().map(f -> "?").reduce(GenericDaoImpl::combineWithComma).orElse("");
    }

    private String tableName() {
        return ReflectionUtils.className(entityClass);
    }

    public String commaSeparatedFieldNames(){
        return fields.stream().map(FieldInfo::getFieldName).reduce(GenericDaoImpl::combineWithComma).orElse("");
    }

    private static String combineWithComma(String x, String y) {
        return x + ',' + y;
    }

}

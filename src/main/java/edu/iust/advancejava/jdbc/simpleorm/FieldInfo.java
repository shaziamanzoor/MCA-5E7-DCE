package edu.iust.advancejava.jdbc.simpleorm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FieldInfo {
    private final Method getter;
    private final Method setter;
    private final String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public Class<?> getDataType(){
        return getter.getReturnType();
    }

    public Object getValue(Object o) throws InvocationTargetException, IllegalAccessException {
        return getter.invoke(o);
    }

    public void setValue(Object o, Object value) throws InvocationTargetException, IllegalAccessException {
        setter.invoke(o, value);
    }

    public FieldInfo(Method getter, Method setter, String fieldName) {
        this.getter = getter;
        this.setter = setter;
        this.fieldName = fieldName;
    }
}

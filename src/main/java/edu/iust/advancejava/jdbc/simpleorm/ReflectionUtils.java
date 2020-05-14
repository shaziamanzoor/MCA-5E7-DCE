package edu.iust.advancejava.jdbc.simpleorm;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReflectionUtils {
    //  A Pattern object is a compiled representation of a regular expression.
    // matches "get|is" then any a capital letter as "." is given "*" indicate any number of characters
    private static Pattern getterPattern = Pattern.compile("(get|is)([A-Z].*)");
// unbounded wild card ? lets you return any value
    public static String className(Class<?> clazz){
        //This method returns the simple name of the underlying class as given in the source code.
        return clazz.getSimpleName();
    }

    public static List<FieldInfo> getFieldNames(Class<?> clazz){
        //getDeclaredMethods() returns an array of method objects representing all the methods defined in this class object
        return Arrays.stream(clazz.getDeclaredMethods())
                .map(ReflectionUtils::getGetter)
                .filter(Optional::isPresent)
                .map(Optional::get)//take out those values form optional
                .collect(Collectors.toList());
    }

    private static Optional<FieldInfo> getGetter(Method getter) {
        Matcher match = getterPattern.matcher(getter.getName());
        if(!match.matches())
            return Optional.empty();
        //if we give 0 the whole reg exp is returned if 1 first group in brackets is returned if 2 then second group
        //getDeclaringClass()It returns the class or interface represented by this Class
        //object (which is a member of another class) and the Class object representing the class in which it was declared.
        //getDeclaredMethods()It returns a Method object that reflects the given declared method of the class or interface
        // as represented by this Class object.
        //as we need the field name we dont need the field getter name eg getName so Name is what we need so we use group(2)
        String fieldName = match.group(2);
        try {
            Method setter = getter.getDeclaringClass().getDeclaredMethod("set" + fieldName, getter.getReturnType());
            return Optional.of(new FieldInfo(getter, setter, makeFirstCharLower(fieldName)));
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    static String makeFirstCharLower(String s) {
        if (s == null){
            return null;
        }

        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }

}

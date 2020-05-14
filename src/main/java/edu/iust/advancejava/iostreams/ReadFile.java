package edu.iust.advancejava.iostreams;
import jdk.internal.util.xml.impl.Input;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ReadFile {
    public static void main(String args[]) {
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader("input.txt"));
            System.out.println(reader.readLine());
        }catch(Exception e) {
            e.getStackTrace();
        } finally {
             if (reader != null)
                 try {
                     reader.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
        }

        try (BufferedReader r = new BufferedReader(new FileReader("/etc/hosts"))){
            System.out.println(r.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

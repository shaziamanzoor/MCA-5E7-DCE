package edu.iust.advancejava.iostreams;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class WriteFile {
    public static void main(String args[]){
        String data = "This will be written to output file";
        try{
            OutputStream out = new FileOutputStream("output.txt");
            byte[] dataBytes = data.getBytes();
            out.write(dataBytes);
            System.out.println("data written to the file");
            out.close();
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}

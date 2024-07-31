package com.example.demo.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component("file")
public class FileWorker {
    public String filePathInput = "/app/input.txt";
    public String filePathOutput = "/app/output.txt";
    
    public void saveToFile(String entry){
        try (FileWriter fileWriter = new FileWriter(filePathOutput);
            BufferedWriter writer = new BufferedWriter(fileWriter)){
            writer.write(entry);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String readFromFile(){
        // try (FileReader fileReader = new FileReader(filePathInput);
        //     BufferedReader reader = new BufferedReader(fileReader)) {
        //     reader.read    
        try (RandomAccessFile reader = new RandomAccessFile(filePathInput, "r")){
            Random rnd = new Random();
            long pointer = rnd.nextLong(reader.length() + 1);
            reader.seek(pointer);
            while (!(reader.readChar() == '\n')) {
                pointer -= 2;
                reader.seek(pointer);
            }
            reader.seek(pointer + 2);
            return reader.readLine();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

}

package com.example.demo.files;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Paths;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component("file")
public class FileWorker {
    public String dir = Paths.get("").toAbsolutePath().toString();
    public String filePathInput = dir + "\\input.txt";
    public String filePathOutput = dir + "\\output.txt";
    
    public void saveToFile(String entry){
        try (FileWriter fileWriter = new FileWriter(filePathOutput)){
            fileWriter.write(entry);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String readFromFile(){
        int num;
        
        try (LineNumberReader reader = new LineNumberReader(new FileReader(filePathInput))) {
            reader.skip(Integer.MAX_VALUE);
            int numberOfLines = reader.getLineNumber();
            Random rnd = new Random();
            num = rnd.nextInt(numberOfLines) + 1;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        try (LineNumberReader reader = new LineNumberReader(new FileReader(filePathInput))) {
            for (String line; (line = reader.readLine()) != null; ) {
                if (reader.getLineNumber() == num) return line;
            }
            return null;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

}

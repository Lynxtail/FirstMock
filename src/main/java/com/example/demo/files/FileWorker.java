package com.example.demo.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component("file")
public class FileWorker {
    public String filePath = "/app/output.txt";
    
    public void saveToFile(String entry){
        try (FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            bufferedWriter.write(entry);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}

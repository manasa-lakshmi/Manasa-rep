package com.ceix.ehs.model.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ReadFile {
    public ReadFile() {
        super();
    }
    
    public ArrayList<String[]> readDataFile(File csvFile){
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = "|";
        ArrayList<String[]> list= new ArrayList<>();
        try {
            int i=0;
            
            br = new BufferedReader(new FileReader(csvFile));
            
            try {
                while ((line = br.readLine()) != null) {
                    if (i>0){
                         String[] vals = line.split(Pattern.quote("|"),-1);
                         list.add(vals);
                    }
                    i++;
                }
            } catch (IOException e) {
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;


     
    }
    
  
}

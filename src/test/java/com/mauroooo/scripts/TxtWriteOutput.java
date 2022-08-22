package com.mauroooo.scripts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;

public class TxtWriteOutput {

    protected static File textFile;
    protected static volatile TxtWriteOutput instance;

    public TxtWriteOutput(){

    }

    public static TxtWriteOutput getInstance(){
        if (instance == null){
            synchronized (TxtWriteOutput.class){
                if (instance == null){
                    instance = new TxtWriteOutput();
                }
            }
        }
        return instance;
    }

    public static File makeTextFile(String fileName) throws IOException {
        textFile = new File(fileName + ".txt");
        if (textFile.createNewFile()) {
            System.out.println("File created under" + textFile.getPath());
        }
        return textFile;
    }

    public static void writeFileHeader(File txtFile, String scenarioName){
        try (FileWriter fw = new FileWriter(txtFile.getAbsoluteFile(), true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter pr = new PrintWriter(bw);) {
            pr.println("\n###" + Calendar.DAY_OF_YEAR + "-" + Calendar.HOUR_OF_DAY + "hs-" + Calendar.MINUTE + "mins-" + scenarioName + "###\n");
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void writeTextFile(String valueName, List<String> links, File txtFile){
        try (FileWriter fw = new FileWriter(txtFile.getAbsoluteFile(), true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter pr = new PrintWriter(bw);) {
            int iter = 1;
            pr.println("\nResults for " + valueName + ":\n");
            for (String link : links) {
                pr.println("Link #" + (iter) + ": " + link + "\n");
                iter++;
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }
}
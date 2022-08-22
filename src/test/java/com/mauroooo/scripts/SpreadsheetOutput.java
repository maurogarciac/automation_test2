package com.mauroooo.scripts;

import org.apache.poi.EmptyFileException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.StandardOpenOption.READ;


public class SpreadsheetOutput {
    protected Path excelFile;
    protected Workbook workbook;

    protected static ConcurrentMap<Path, SpreadsheetOutput> instances = new ConcurrentHashMap<>();


    private SpreadsheetOutput(Path excelPath) {
        excelFile = excelPath;
        open();
    }

    public static SpreadsheetOutput getInstance(Path excelFile) {
        /*
        SpreadsheetOutput open;
        if(instances.get(excelFile) == null){
            synchronized (SpreadsheetOutput.class) {
                if(instances.get(excelFile) == null){
                    open = new SpreadsheetOutput(excelFile);
                }
            }
        }
        instances.put(excelFile, open);
        return open;
        */
        return instances.computeIfAbsent(excelFile, SpreadsheetOutput::new);
    }

    protected void open() {
        try {
            InputStream fileInput = newInputStream(excelFile, READ);
            workbook = new XSSFWorkbook(fileInput);
            fileInput.close();
        } catch (EmptyFileException | IOException exception) {
            workbook = new XSSFWorkbook();
        }
    }

    public synchronized void addSheet(String valueName, List<String> links) {
        Row row;
        Sheet spreadsheet;

        if ((spreadsheet = workbook.getSheet(valueName)) == null) {
            spreadsheet = workbook.createSheet(valueName);
        }
        row = spreadsheet.createRow(spreadsheet.getLastRowNum() + 1);
        int iter = 1;
        row.createCell(0).setCellValue("\nResults for " + valueName + ":\n");
        spreadsheet.autoSizeColumn(0);
        for (String link : links) {
            row.createCell(iter).setCellValue("Link #" + iter + ": " + link);
            spreadsheet.autoSizeColumn(iter);
            iter++;
        }
    }

    public synchronized void writeFile() throws IOException {
        OutputStream fileOutput = newOutputStream(excelFile);
        workbook.write(fileOutput);
        fileOutput.close();
    }
}

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
    protected static Path excelFile;
    protected static Row row;
    protected static Workbook workbook;
    protected static Sheet spreadsheet;
    protected static InputStream fileInput;
    protected static OutputStream fileOutput;

    protected static volatile SpreadsheetOutput open;
    protected static ConcurrentMap<Path, SpreadsheetOutput> instances = new ConcurrentHashMap<>();


    private SpreadsheetOutput(Path excelPath) {
        excelFile = excelPath;
    }

    public static SpreadsheetOutput open(Path excelFile) throws IOException {

        if(instances.get(excelFile) == null){
            synchronized (SpreadsheetOutput.class) {
                if(instances.get(excelFile) == null){
                    open = new SpreadsheetOutput(excelFile);
                }
            }
        }
        instances.put(excelFile, open);
        return open;

        //return instances.computeIfAbsent(open.excelFile, SpreadsheetOutput::new);

    }

    public static void writeExcelFile(String valueName, List<String> links) throws IOException {
        fileInput = newInputStream(excelFile, READ);
        try {
            workbook = new XSSFWorkbook(fileInput);
        } catch (EmptyFileException exception) {
            System.out.println(exception);
            workbook = new XSSFWorkbook();
        }
        if (workbook.getSheetIndex(valueName) >= 0) {
            spreadsheet = workbook.getSheet(valueName);
        } else {
            spreadsheet = workbook.createSheet(valueName);
        }
        if (spreadsheet.getRow(0) == null) {
            row = spreadsheet.createRow(0);
        } else {
            row = spreadsheet.createRow(spreadsheet.getLastRowNum() + 1);
        }
        int iter = 1;
        row.createCell(0).setCellValue("\nResults for " + valueName + ":\n");
        spreadsheet.autoSizeColumn(0);
        for (String link : links) {
            row.createCell(iter).setCellValue("Link #" + iter + ": " + link);
            spreadsheet.autoSizeColumn(iter);
            iter++;
        }
    }

    public static void closeExcelFile() throws IOException {
        fileOutput = newOutputStream(excelFile);
        workbook.write(fileOutput);
    }
}

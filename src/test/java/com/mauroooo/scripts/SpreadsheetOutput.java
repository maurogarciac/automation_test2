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
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.StandardOpenOption.READ;


public class SpreadsheetOutput {
    //protected static final Map<Path, SpreadsheetOutput> openFiles = new HashMap<>();
    protected static OutputStream fileOutput;
    protected static InputStream fileInput;
    //protected static volatile SpreadsheetOutput instance;
    protected static volatile SpreadsheetOutput open;
    protected List<String> links;
    protected static String spreadSheetFileName;

    public SpreadsheetOutput(List<String> list) {

    }



    protected static ConcurrentMap<Path, SpreadsheetOutput> instances = new ConcurrentHashMap<>();


    public static SpreadsheetOutput open(String valueName, SpreadsheetOutput spreadsheetOutput) throws IOException {
        Path excelFile = Paths.get(spreadSheetFileName + ".xlsx").toAbsolutePath();
        instances.put(excelFile, spreadsheetOutput);
        fileInput = newInputStream(excelFile, READ);

        if(instances.get(excelFile) == null){
            synchronized (SpreadsheetOutput.class) {
                open = new SpreadsheetOutput();
            }
        }
        return open;
    }

    public static void writeExcelFile(String valueName, List<String> links) throws IOException {
        Workbook workbook;
        Sheet spreadsheet;
        Row row;
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
        fileOutput = newOutputStream(multitonInst.get());
        workbook.write(fileOutput);

    }
}
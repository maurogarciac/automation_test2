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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import static java.nio.file.StandardOpenOption.READ;


public class SpreadsheetOutput {
    protected static final Map<Path, SpreadsheetOutput> openFiles = new HashMap<>();
    protected OutputStream fileOutput;
    protected static volatile SpreadsheetOutput instance;
    //classloader
    public enum MultiTest { ONE,TWO,THREE }
    public static SpreadsheetOutput getInstance(){

        if (instance == null){
            synchronized (SpreadsheetOutput.class){
                if (instance == null) {
                    instance = new SpreadsheetOutput();
                }
            }
        }
        return instance;
    }
    //convertir esto en un multiton que dada la instancia devuelva un valor diferente en el map
    // en vez de un getINstance va a ser el open
    // va a ser absoluto y completamente dinamico
    // como Mapa usar algo de java.util.concurrent >> puede ser un concurrentHashmap por ejemplo
    public static SpreadsheetOutput open(String fileName) {

        Path excelFile = Paths.get(fileName).toAbsolutePath();

        return null;
    }

    // thread safe > synchronized
    // una especie de singleton que se llama multiton
    //singleton tiene una sola instancia pero el multiton tiene multiples instancias con nombres diferentes >> enum es un multiton
    // pero como mis nombres son dinamicos >> write.file(fileName)
    // tiene que fijarse si el archivo esta abierto y seguir escribiendo
    //por ahi convine mantener el outputStream hasta el final
    // Como hacer varios incrementos dentro de la misma ejecucion
    // Map<Path, SpreadsheetOutput> que el open del archivo sea protected y llamarlo dentro del writeFile y que use ese archivo
    public static void writeExcelFile(String valueName, List<String> links, String sheetName) throws IOException {
        Path excelFile = Paths.get("ExcelOutput.xlsx");
        InputStream fileInput = newInputStream(excelFile, READ);
        Workbook workbook;
        Sheet spreadsheet;
        Row row;
        try {
            workbook = new XSSFWorkbook(fileInput);
        } catch (EmptyFileException exception) {
            System.out.println(exception);
            workbook = new XSSFWorkbook();
        }
        if (workbook.getSheetIndex(sheetName) >= 0) {
            spreadsheet = workbook.getSheet(sheetName);
        } else {
            spreadsheet = workbook.createSheet(sheetName);
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
        OutputStream fileOutput = newOutputStream(excelFile);
        workbook.write(fileOutput);

    }
}
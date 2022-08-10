package com.mauroooo.scripts;

import java.util.Map;

import io.cucumber.java.Scenario;
import org.apache.poi.ss.formula.WorkbookEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.FileOutputStream;
import java.util.TreeMap;

public class SpreadsheetOutput {

    // Workbook workbook;
    private Sheet spreadsheet;
    private Row row;
    protected Scenario scenario;


    // This data needs to be written (Object[])
    Map<String, Object[]> studentData = new TreeMap<String, Object[]>();

    public void createExcel(){
        //Workbook workbook = new Workbook();
        //spreadsheet = workbook.createSheet(" Student Data ");
        //return workbook;
    }

    public void writeExcelFile(){
        String scenarioName = scenario.getName();

    }

}
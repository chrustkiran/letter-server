package com.road.letter.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

@Service
public class FileProcessorImpl implements FileProcessor {
    private static final String ROWS = "rows";
    private static final String ROAD_ID = "RoadId";

    @Value("${location}")
    String fileLocation;

    @Override
    public HashMap<String, HashSet<Integer>> getSubjects() throws IOException {
        HashMap<String, HashSet<Integer>> subjects = new HashMap<>();
        Iterator<Row> rowIterator = null;
        try {
            rowIterator = readXlsx().iterator();
            int rowInd = 0;
            while (rowIterator.hasNext()) {
                rowInd++;
                if (rowInd < 3) {
                    rowIterator.next();
                    continue;
                }
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                int cellInd = 0;
                while (cellIterator.hasNext()) {
                    cellInd++;
                    if (cellInd != 2) {
                        cellIterator.next();
                        continue;
                    }
                    Cell cell = cellIterator.next();
                    if (!subjects.containsKey(cell.getStringCellValue())) {
                        System.out.println(cell.getStringCellValue());
                        HashSet<Integer> rows = new HashSet<>();
                        rows.add(rowInd);
                        subjects.put(cell.getStringCellValue(), rows);
                    } else {
                        subjects.get(cell.getStringCellValue()).add(rowInd);
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        }
        return subjects;
    }

    @Override
    public HashMap<String, ArrayList<String[]>> getLetters(HashMap<String, Object> filterObj) throws IOException {
        HashMap<String, ArrayList<String[]>> lettersDateWise = new HashMap<>();
        if (filterObj.get(ROWS) instanceof ArrayList) {
            ArrayList<Integer> rowIndexes = (ArrayList<Integer>) filterObj.get(ROWS);
            Iterator<Row> rowIterator = null;
            try {
                for (Integer rowId : rowIndexes) {
                    XSSFRow row = readXlsx().getRow(rowId - 1);

                    if (row.getCell(2) != null && filterObj.get(ROAD_ID).toString().equals(getCellStringVal(row.getCell(2)))) {
                        String date = getCellStringVal(row.getCell(4));
                        try {
                            if (lettersDateWise.containsKey(date)) {
                                String[] letters = new String[2];
                                letters[0] = getCellStringVal(row.getCell(5));
                                letters[1] = getCellStringVal(row.getCell(8));
                                lettersDateWise.get(date).add(letters);
                            } else {
                                ArrayList<String[]> lettersArr = new ArrayList<>();
                                String[] letters = new String[2];
                                letters[0] = getCellStringVal(row.getCell(5));
                                letters[1] = getCellStringVal(row.getCell(8));
                                lettersArr.add(letters);
                                lettersDateWise.put(date, lettersArr);
                            }
                        } catch (NullPointerException e) {
                            System.out.println(e);
                        }
                    }
                }
            } catch (IOException e) {
                throw e;
            }

        }
        return lettersDateWise;
    }

    private XSSFSheet readXlsx() throws IOException {
        String filePath = fileLocation + "/" + FileServiceImpl.selectedFile;
        File myFile = new File(filePath);
        FileInputStream fis = new FileInputStream(myFile);
        XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
        return mySheet;
    }

    private String getCellStringVal(XSSFCell cell) {
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                return (int)cell.getNumericCellValue() + "";
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";
            default:
                return "";
        }
    }

}

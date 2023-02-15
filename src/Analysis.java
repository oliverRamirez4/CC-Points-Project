import jxl.read.biff.BiffException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analysis {

    Map<Integer, List<String>> data;

    public Analysis() {
        data = new HashMap<>();
    }

    // Using apache.poi.ooxml.scemas
    public Map<Integer, List<String>> readJExcel(String fileLocation)
            throws IOException, BiffException, InvalidFormatException {

        //FileInputStream file = new FileInputStream(fileLocation);
        XSSFWorkbook workbook = new XSSFWorkbook(new File(fileLocation));

        Sheet sheet = workbook.getSheetAt(201);

        data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        data.get(i).add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        data.get(i).add(String.valueOf(cell.getNumericCellValue()));
                        break;
                    case BOOLEAN:
                        break;
                    case FORMULA:
                        data.get(i).add(String.valueOf(cell.getCellFormula()));
                        break;
                    default:
                        data.get(i).add(" ");
                }
            }
            i++;
        }

        return data;
    }

    public String getPrintData() {
        String printData = "";
        for (Integer key : data.keySet()) {
            printData += (key + " = \t");
            for (Object value : data.get(key).toArray()) {
                printData += (String) value;
                printData += "\t\t";
            }
            printData += "\n";
        }
        return printData;
    }

    public static void main(String[] args) {
        try {
            Analysis analysis = new Analysis();
            analysis.readJExcel("./src/CP222 - Projet/2020-11-19 - Course Demand and Point Distribution.xlsx");
            System.out.println(analysis.getPrintData());
        } catch (IOException | InvalidFormatException | BiffException e) {
            throw new RuntimeException(e);
        }
    }

//    copyWordCounter wordCounter = new copyWordCounter();
//
//    FileOutputStream fileOut = new FileOutputStream("Data.ser");
//    ObjectOutputStream out = new ObjectOutputStream(fileOut);
//        out.writeObject(wordCounter.wordMapBi);
//        out.writeObject(wordCounter.wordMapUni);
//        out.close();
//        fileOut.close();
//
//        System.out.println("object info saved");
}

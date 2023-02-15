import jxl.read.biff.BiffException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {

    private Map<Integer, List<String>> data;

    public Data() {
        data = new HashMap<>();
    }

    // Using apache.poi.ooxml.scemas
    public Map<Integer, List<String>> readJExcel(String fileLocation, int fileNumber)
            throws IOException, BiffException, InvalidFormatException {

        //FileInputStream file = new FileInputStream(fileLocation);
        XSSFWorkbook workbook = new XSSFWorkbook(new File(fileLocation));

        Sheet sheet = workbook.getSheetAt(fileNumber);

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
                        data.get(i).add(String.valueOf(cell.getBooleanCellValue()));
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

    //This function allows you to get the names of courses.
    public String getCourseName(String fileLocation, int fileNumber) throws IOException, InvalidFormatException {
        String output;

        XSSFWorkbook workbook = new XSSFWorkbook(new File(fileLocation));

        Sheet sheet = workbook.getSheetAt(fileNumber);

        output = sheet.getRow(1).getCell(2).getStringCellValue();

        String id = "";
        for (int i = 12; i <= 17; i++) id += output.toCharArray()[i];
        return id;
    }

    public Map<Integer, List<String>> getData() {
        return data;
    }


    public static void main(String[] args) throws IOException {
        try {
            Data analysis = new Data();
            String url = "./src/CP222 - Projet/2020-11-19 - Course Demand and Point Distribution.xlsx", course; int block;
            course = analysis.getCourseName(url, 1);
            System.out.println(course);
            analysis.readJExcel("./src/CP222 - Projet/2020-11-19 - Course Demand and Point Distribution.xlsx", 0);
            //System.out.println(analysis.getPrintData());
        } catch (IOException | InvalidFormatException | BiffException e) {
            throw new RuntimeException(e);
        }/*

        Data dataMain = new Data();

        for (int i = 0; i < 10; i++) {
            FileOutputStream fileOut = new FileOutputStream("src/usableData/2021S/Data" + i + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            try {
                dataMain.readJExcel(url, i);
            } catch (BiffException | InvalidFormatException e){
                throw new RuntimeException(e);
            }
            out.writeObject(dataMain.getData());
            out.close();
            fileOut.close();

            System.out.println("object info saved");

        }

        HashMap<String, Integer> map = null;

        // Deserialize the HashMap
        try (FileInputStream fileIn = new FileInputStream("Data.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            map = (HashMap<String, Integer>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

}

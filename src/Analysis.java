import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analysis {
    public Analysis() {
        
    }

    // Using apache.poi.ooxml.scemas
    public Map<Integer, List<String>> readJExcel(String fileLocation)
            throws IOException, BiffException, InvalidFormatException {

        //FileInputStream file = new FileInputStream(fileLocation);
        XSSFWorkbook workbook = new XSSFWorkbook(new File(fileLocation));

        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<String>());
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
                        data.get(Integer.valueOf(i)).add(" ");
                }
            }
            i++;
        }

        return data;
    }

    public static void main(String[] args) {
        try {
            System.out.println(new Analysis().readJExcel("./src/CP222 - Projet/2020-11-19 - Course Demand and Point Distribution.xlsx"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (BiffException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
    }
}

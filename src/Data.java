import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class Data {

    /**
     * Level 1: Course ID
     * Level 2: Block number
     * Level 3: number of points
     * Level 4: number of people that put that many points
     */
    private Map<String, Map<String, Map<Integer, Integer>>> coursePointsData;
    private Map<Integer, List<String>> data;

    /**
     * Level 1: Course ID
     * Level 2: Block Number
     * Level 3: (1) minimum points, (2) number of seats, (3) demand
     */
    private Map<String, Map<String, List<Integer>>> minMaxPoints;

    /**
     * Level 1: Semester
     * Level 2: Year
     * Level 3: Data
     */
    public Map<String, Map<String, Course>> courseData;

    public XSSFWorkbook workbook;

    public Data(String fileLocation) throws IOException, InvalidFormatException {
        data = new HashMap<>();
        coursePointsData = new HashMap<>();
        minMaxPoints = new HashMap<>();
        workbook = new XSSFWorkbook(new File(fileLocation));
        courseData = new HashMap<>();
    }

    public Data() {
        data = new HashMap<>();
        coursePointsData = new HashMap<>();
        minMaxPoints = new HashMap<>();
        courseData = new HashMap<>();
    }

    public Map<String, Map<String, Map<Integer, Integer>>> getCoursePointsData() {
        return coursePointsData;
    }

    // Using apache.poi.ooxml.scemas
                                        // NOT BEING USED
    public Map<Integer, List<String>> readJExcel(int fileNumber)
            throws IOException, InvalidFormatException {

        //FileInputStream file = new FileInputStream(fileLocation);

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

    // Adds the course points to the Data file
    public void addToCoursePointsData(int fileNumber) throws IOException, InvalidFormatException {
        String name = getCourseID(fileNumber);
        String block = getCourseBlock(fileNumber);
        coursePointsData.put(name, new HashMap<>());
        coursePointsData.get(name).put(block, new HashMap<>());

        Sheet sheet = workbook.getSheetAt(fileNumber);

        Integer j = 0;
        int i = 21;
        while (true) {
            try {
                j = Integer.valueOf((int)sheet.getRow(i).getCell(8).getNumericCellValue());
            } catch (NullPointerException e) {
                break;
            }
            coursePointsData.get(name).get(block).put(j, Integer.valueOf((int)sheet.getRow(i).getCell(7).getNumericCellValue()));
            i++;
        }
    }

    // Adds the course minimum, maximum, etc. points to the Data file
    public void addToMinMaxPointsData(int fileNumber) throws IOException, InvalidFormatException {
        //Data assurance so I don't put something into nothing
        String name = getCourseID(fileNumber);
        String block = getCourseBlock(fileNumber);
        minMaxPoints.put(name, new HashMap<>());
        minMaxPoints.get(name).put(block, new ArrayList<>());

        Sheet sheet = workbook.getSheetAt(fileNumber);

        //Add the minimum points
        int i;
        try {
            i = (int) sheet.getRow(4).getCell(7).getNumericCellValue();
        } catch (NullPointerException n) {
            i = 0;
        }
        minMaxPoints.get(name).get(block).add(0, i);

        //Add the number of seats
        try {
            i = Integer.valueOf(charsBtwn(sheet.getRow(1).getCell(6).getStringCellValue(), 7, 8));
        } catch (NullPointerException n) {
            i = 0;
        }
        minMaxPoints.get(name).get(block).add(1, i);

        //Add the seat demand
        try {
            i = Integer.valueOf(charsBtwn(sheet.getRow(2).getCell(6).getStringCellValue(), 8, 9));
        } catch (NullPointerException n) {
            i = 0;
        }
        minMaxPoints.get(name).get(block).add(2, i);
    }

    public Map<String, Map<String, List<Integer>>> getMinMaxPoints() {
        return minMaxPoints;
    }

    public String getPrintableMinMaxPointsData() {
        String printData = "";
        for (String course : minMaxPoints.keySet()) {
            printData += ("Course " + course + "\n");
            for (String block : minMaxPoints.get(course).keySet()) {
                printData += block;
                printData += "\t";

                printData += "Minimum points: ";
                printData += minMaxPoints.get(course).get(block).get(0);
                printData += "\t";

                printData += "Seat total: ";
                printData += minMaxPoints.get(course).get(block).get(1);
                printData += "\t";

                printData += "Seat demand: ";
                printData += minMaxPoints.get(course).get(block).get(2);
                printData += "\t";
            }
            printData += "\n";
        }
        return printData;
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
    public String getCourseID(int fileNumber) throws IOException, InvalidFormatException {
        String output;

        Sheet sheet = workbook.getSheetAt(fileNumber);

        output = sheet.getRow(1).getCell(2).getStringCellValue();

        String id = "";
        try {
            for (int i = 11; i <= 15; i++) id += output.toCharArray()[i];
        } catch (ArrayIndexOutOfBoundsException a) {;}
        return id;
    }

    //This function allows you to get the numbers of courses.
    public String getCourseBlock(int fileNumber) throws IOException, InvalidFormatException {
        String output;

        Sheet sheet = workbook.getSheetAt(fileNumber);

        output = sheet.getRow(1).getCell(2).getStringCellValue();

        String id = "";
        try {
            for (int i = 17; i <= 18; i++) id += output.toCharArray()[i];

            //If it's only one class, return the block. If it is multiple blocks, return the block range
            if (id.toCharArray()[0] == id.toCharArray()[1]) id = String.valueOf(id.charAt(0));
            else { String temp = ""; temp += (id.charAt(0) + "-" + id.charAt(1)); id = temp; }
        } catch (ArrayIndexOutOfBoundsException a) {;}
        return id;
    }

    private String charsBtwn(String input, int start, int end) {
        String id = "";
        try {
            for (int i = start; i <= end; i++) id += input.toCharArray()[i];
        } catch (ArrayIndexOutOfBoundsException a) {;}
        return id;
    }

    public Map<Integer, List<String>> getData() {
        return data;
    }

    public String[] getCourseList(){
        Set<String> map = null;

        // Deserialize the HashMap
        try (FileInputStream fileIn = new FileInputStream("./src/CourseCount/2021F.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            map = ((HashMap<String, HashMap<Integer, Integer>>) in.readObject()).keySet();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (String[]) map.toArray();
    }

    /**
     * @param courseID
     * @param block
     * @param semester (format XXXXN with XXXX being the year and N being either S (Spring) or F (Fall)
     * @return map
     */
    public HashMap<String, HashMap<Integer, Integer>> getFileData(String courseID, String block, String semester) {
        HashMap<String, HashMap<Integer, Integer>> map = null;

        // Deserialize the HashMap
        try (FileInputStream fileIn = new FileInputStream("./src/usableData/" + semester + "/" + courseID + block + ".ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            map = (HashMap<String, HashMap<Integer, Integer>>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }



    public static void main(String[] args) throws IOException, InvalidFormatException {



        /*try {
            String url = "./src/CP222 - Projet/2021-05-03 - Course Demand and Point Distribution.xlsx", courseID, courseBlock; int block;

            Data dataMain = new Data(url);

        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }*/

        /*Data dataMain = new Data(url);
            for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
                courseID = dataMain.getCourseID(i);
                courseBlock = dataMain.getCourseBlock(i);
                //FileOutputStream fileOut = new FileOutputStream("src/usableData/2021S/" + courseID + courseBlock + ".ser");
                //ObjectOutputStream out = new ObjectOutputStream(fileOut);
                try {
                    dataMain.addToMinMaxPointsData(i);
                } catch (InvalidFormatException e){
                    throw new RuntimeException(e);
                }
                //out.writeObject(dataMain.getCoursePointsData());
                //out.close();
                //fileOut.close();

                //System.out.println(dataMain.getPrintableMinMaxPointsData());
            }

            System.out.println(Arrays.toString(dataMain.getCourseList(dataMain)));*/


            /*Data analysis = new Data(url);
            for (int i = 0; i < analysis.workbook.getNumberOfSheets(); i++) {
                courseID = analysis.getCourseID(i);
                courseBlock = analysis.getCourseBlock(i);
                //analysis.addToCoursePointsData(i);
                System.out.println(courseID);
                System.out.println(courseBlock);
                //System.out.println(analysis.getCoursePointsData());
                System.out.println();
            }*/


        /*String url = "./src/CP222 - Projet/2021-05-03 - Course Demand and Point Distribution.xlsx", courseID, courseBlock; int block;
        Data dataMain = new Data(url);
            for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
                courseID = dataMain.getCourseID(i);
                courseBlock = dataMain.getCourseBlock(i);
                FileOutputStream fileOut = new FileOutputStream("src/usableData/2021F/" + courseID + courseBlock + ".ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                try {
                    dataMain.addToCoursePointsData(i);
                } catch (InvalidFormatException e){
                    throw new RuntimeException(e);
                }
                out.writeObject(dataMain.getCoursePointsData().get(courseID).get(courseBlock));
                out.close();
                fileOut.close();

                System.out.println("object info saved");

            }
            System.out.println("2021F complete");

        url = "./src/CP222 - Projet/2020-11-19 - Course Demand and Point Distribution.xlsx";
        dataMain = new Data(url);

        for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
            courseID = dataMain.getCourseID(i);
            courseBlock = dataMain.getCourseBlock(i);
            FileOutputStream fileOut = new FileOutputStream("src/usableData/2021S/" + courseID + courseBlock + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            try {
                dataMain.addToCoursePointsData(i);
            } catch (InvalidFormatException e){
                throw new RuntimeException(e);
            }
            out.writeObject(dataMain.getCoursePointsData().get(courseID).get(courseBlock));
            out.close();
            fileOut.close();

            System.out.println("object info saved");

        }
        System.out.println("2021S complete");

            url = "./src/CP222 - Projet/2021-12-07 - Course Demand and Point Distribution.xlsx";
            dataMain = new Data(url);

            for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
                courseID = dataMain.getCourseID(i);
                courseBlock = dataMain.getCourseBlock(i);
                FileOutputStream fileOut = new FileOutputStream("src/usableData/2022S/" + courseID + courseBlock + ".ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                try {
                    dataMain.addToCoursePointsData(i);
                } catch (InvalidFormatException e){
                    throw new RuntimeException(e);
                }
                out.writeObject(dataMain.getCoursePointsData().get(courseID).get(courseBlock));
                out.close();
                fileOut.close();

                System.out.println("object info saved");

            }
            System.out.println("2022S complete");

            url = "./src/CP222 - Projet/2022-05-03 - Fall 2022 - Course Demand and Point Distribution.xlsx";
            dataMain = new Data(url);

            for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
                courseID = dataMain.getCourseID(i);
                courseBlock = dataMain.getCourseBlock(i);
                FileOutputStream fileOut = new FileOutputStream("src/usableData/2022F/" + courseID + courseBlock + ".ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                try {
                    dataMain.addToCoursePointsData(i);
                } catch (InvalidFormatException e){
                    throw new RuntimeException(e);
                }
                out.writeObject(dataMain.getCoursePointsData().get(courseID).get(courseBlock));
                out.close();
                fileOut.close();

                System.out.println("object info saved");

            }
            System.out.println("2022F complete");

            url = "./src/CP222 - Projet/2022-12-06 - Course Demand and Point Distribution.xlsx";
            dataMain = new Data(url);

            for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
                courseID = dataMain.getCourseID(i);
                courseBlock = dataMain.getCourseBlock(i);
                FileOutputStream fileOut = new FileOutputStream("src/usableData/2023S/" + courseID + courseBlock + ".ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                try {
                    dataMain.addToCoursePointsData(i);
                } catch (InvalidFormatException e){
                    throw new RuntimeException(e);
                }
                out.writeObject(dataMain.getCoursePointsData().get(courseID).get(courseBlock));
                out.close();
                fileOut.close();

                System.out.println("object info saved");

            }
            System.out.println("2023S complete");*/

        /*String url = "./src/CP222 - Projet/2021-05-03 - Course Demand and Point Distribution.xlsx", courseID, courseBlock; int block;
        Data dataMain = new Data(url);

        FileOutputStream fileOut = new FileOutputStream("src/CourseCount/2021F.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
            try {
                dataMain.addToMinMaxPointsData(i);
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        }
        out.writeObject(dataMain.getMinMaxPoints());
        out.close();
        fileOut.close();

        System.out.println("object info saved");
        System.out.println("2021F complete");

        url = "./src/CP222 - Projet/2020-11-19 - Course Demand and Point Distribution.xlsx";
        dataMain = new Data(url);

        fileOut = new FileOutputStream("src/CourseCount/2021S.ser");
        out = new ObjectOutputStream(fileOut);
        for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
            try {
                dataMain.addToMinMaxPointsData(i);
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        }
        out.writeObject(dataMain.getMinMaxPoints());
        out.close();
        fileOut.close();

        System.out.println("object info saved");
        System.out.println("2021S complete");


        url = "./src/CP222 - Projet/2021-12-07 - Course Demand and Point Distribution.xlsx";
        dataMain = new Data(url);

        fileOut = new FileOutputStream("src/CourseCount/2022S.ser");
        out = new ObjectOutputStream(fileOut);
        for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
            try {
                dataMain.addToMinMaxPointsData(i);
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        }
        out.writeObject(dataMain.getMinMaxPoints());
        out.close();
        fileOut.close();

        System.out.println("object info saved");
        System.out.println("2022S complete");


        url = "./src/CP222 - Projet/2022-05-03 - Fall 2022 - Course Demand and Point Distribution.xlsx";
        dataMain = new Data(url);

        fileOut = new FileOutputStream("src/CourseCount/2022F.ser");
        out = new ObjectOutputStream(fileOut);
        for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
            try {
                dataMain.addToMinMaxPointsData(i);
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        }
        out.writeObject(dataMain.getMinMaxPoints());
        out.close();
        fileOut.close();

        System.out.println("object info saved");
        System.out.println("2022F complete");


        url = "./src/CP222 - Projet/2022-12-06 - Course Demand and Point Distribution.xlsx";
        dataMain = new Data(url);

        fileOut = new FileOutputStream("src/CourseCount/2023S.ser");
        out = new ObjectOutputStream(fileOut);
        for (int i = 0; i < dataMain.workbook.getNumberOfSheets(); i++) {
            try {
                dataMain.addToMinMaxPointsData(i);
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        }
        out.writeObject(dataMain.getMinMaxPoints());
        out.close();
        fileOut.close();

        System.out.println("object info saved");
        System.out.println("2023S complete");*/

        //analysis.readJExcel("./src/CP222 - Projet/2020-11-19 - Course Demand and Point Distribution.xlsx", 0);
        //System.out.println(analysis.getPrintData());
    }

}

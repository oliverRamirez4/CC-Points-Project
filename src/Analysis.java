import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class Analysis {

    public Analysis() {
        //
    }

    enum Semesters {
        S2021 (0), F2021 (1), S2022 (2), F2022 (3), S2023 (4);
        private final int magnitude;
        Semesters(int magnitude) {
            this.magnitude = magnitude;
        }

        public Integer toInteger() {
            return magnitude;
        }

        public Semesters toSemesters(String input) {
            String output;
            switch (input) {
                case "2021S":
                    return S2021;
                case "2021F":
                    return F2021;
                case "2022S":
                    return S2022;
                case "2022F":
                    return F2022;
                case "2023S":
                    return S2023;
                default:
                    return null;
            }
        }
    }
    public static Integer getNextMinPoints(String course) {
        Data data = new Data();
        HashMap<String, HashMap<String, HashMap<String, Course>>> allData = data.allData;

        ArrayList<Integer> points = new ArrayList<>();
        for (String info : allData.get(course).keySet()) {
            points.add(allData.get(course).get(info).get(Data.charsBtwn(course, 5, course.length() - 1)).getMinPoints());
        }
        for (int i = 0; i < points.size(); i++) {
            try {
                if(points.get(i) == null) {
                    points.remove(i);
                    i--;
                }
            } catch (NullPointerException n) {
                break;
            }
        }
        int output = 0;
        if (points.size() == 1) return points.get(0);
        else {
            output = points.get(points.size() - 1);
            output += (points.get(points.size() - 1) - points.get(points.size() - 2));
        }
        return output;
    }

    // Given a course ID give back some sort of information
    public String courseInfo() {
        String coursePoints = "";
        Data data = null;
        try {
            data = new Data("/");
            for (Map.Entry<String, Map<String, Map<Integer,Integer>>> course : data.getCoursePointsData().entrySet())
                for (Map.Entry<String, Map<Integer, Integer>> block : data.getCoursePointsData().get(course).entrySet())
                    for (Map.Entry<Integer, Integer> entry : data.getCoursePointsData().get(course).get(block).entrySet()) coursePoints = "Number of points used: " + entry.getKey() + ", Number of people = " + entry.getValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }
        return coursePoints;
    }

    public static void main(String[] args) {
        System.out.println(getNextMinPoints("AS205"));
    }

}

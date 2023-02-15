import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Analysis {

    public Analysis() {
        //
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

}

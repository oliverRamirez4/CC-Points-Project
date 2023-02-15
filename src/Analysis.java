import java.util.HashMap;
import java.util.Map;

public class Analysis {

    public Analysis() {
        //
    }

    // Given a course ID give back some sort of information
    public String courseInfo() {
        String coursePoints = "";
        Data data = new Data();
        for (Map.Entry<String, Map<Integer,Integer>> course : data.getCoursePointsData().entrySet()) {
            for (Map.Entry<Integer, Integer> entry : data.getCoursePointsData().get(course).entrySet()) {
                coursePoints = "Number of points used: " + entry.getKey() + ", Number of people = " + entry.getValue();
            }
        }
        return coursePoints;
    }

}

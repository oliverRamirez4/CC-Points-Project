import java.util.Map;

public class Analysis {

    public Analysis() {
        Data data = new Data();
    }

    // Given a course ID give back some sort of information
    public String courseInfo() {
        String coursePoints = "";
        for (Map.Entry<Integer,Integer> entry : data.getCoursePointsData.entrySet()) {
            coursePoints = "Number of points used: " + entry.getKey() + ", Number of people = " + entry.getValue();
        }
        return coursePoints;
    }

}

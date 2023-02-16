import javax.swing.*;
import java.util.HashMap;

//this class might be unnecessary
// don't need to work on it
public class Course extends JPanel {

    public String courseID, blockNumber;
    public int minPoints;
    public HashMap<Integer, Integer> pointBids;

    public Course(String courseID, String blockNumber, int minPoints, HashMap<Integer, Integer> pointBids) {
        this.courseID = courseID;
        this.blockNumber = blockNumber;
        this.minPoints = minPoints;
        this.pointBids = pointBids;
    }

    public Course(String courseID, String blockNumber, int minPoints) {
        this.courseID = courseID;
        this.blockNumber = blockNumber;
        this.minPoints = minPoints;
        this.pointBids = null;
    }

    public Course() {
        this.courseID = "";
        this.blockNumber = "";
        this.minPoints = -1;
        pointBids = null;
    }

    public String getCourseID(){
        return courseID;
    }

    public int getMinPoints(){
        return minPoints;
    }

    public String getBlockNumber(){
        return blockNumber;
    }

    public HashMap<Integer, Integer> getPointBids() {
        return pointBids;
    }

}
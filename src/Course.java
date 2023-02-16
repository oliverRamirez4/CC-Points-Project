import javax.swing.*;

//this class might be unnecessary
// don't need to work on it
public class Course extends JPanel {

    public String courseID, blockNumber;
    public int minPoints;

    public Course(String courseID, String blockNumber, int minPoints) {
        this.courseID = courseID;
        this.blockNumber = blockNumber;
        this.minPoints = minPoints;
    }

    public Course() {
        this.courseID = "";
        this.blockNumber = "";
        this.minPoints = -1;
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


}
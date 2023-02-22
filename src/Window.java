import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Window {
    //create all the necessary components for the window
    Data data;
    JFrame topFrame;
    JPanel startPanel, coursePanel;
    JLabel CCLogo, avg;
    String[] classList;
    String[] blockList;
    JComboBox classSelector;
    JButton goButton, doneButton;
    String [] courses;

    public Window(){
        //make Data object
        data=new Data();

        //create the JFrame
        topFrame = new JFrame();

        //create the JPanel and add it to the screen
        startPanel = new JPanel();

        //create the JPanel that will go to course information
        coursePanel=new JPanel();

        avg = new JLabel();

        //Create the border that holds the title for CoursePanel

        //create a JLabel containing the CC Logo
        CCLogo = new JLabel(new ImageIcon("src/CC-Logo-Stacked.png"));

        //create a list of all of the courses and sort it
        classList = data.getAllData().get("2023S").keySet().toArray(String[]::new);
            Arrays.sort(classList);

        //create a list of all the different blocks a class might have
        blockList = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "H", "5-8","5-6", "6-7", "5'7", "6-8", "7-8", "1-4", "3-4","2-4","1-2","2-3", "1-3" };

        //create drop down menu of all of the classes
        classSelector=new JComboBox(classList);

        //create the buttons to go back and forth between pages
        goButton=new JButton("go");
        doneButton = new JButton("Done");

    }


//the display method is used to display the GUI for the point calculator
    public void display(){
        //display the JFrame
        topFrame.setSize(600, 300);
        topFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topFrame.setTitle("Colorado College Point Calculator");

        //StartPanel is the first thing the user sees containing a drop down menu of all classes
        //add all the components necessary for the startPanel
        startPanel.setBackground(Color.LIGHT_GRAY);
        startPanel.setLayout(new BoxLayout(startPanel,BoxLayout.X_AXIS));
        startPanel.add(CCLogo);
        startPanel.add(classSelector);
        startPanel.add(goButton);
        topFrame.add(startPanel);

        //give the Course Panel a box layout manager
        coursePanel.setLayout(new BoxLayout(coursePanel,BoxLayout.PAGE_AXIS));


        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //change which panel is visible
                startPanel.setVisible(false);
                coursePanel.setVisible(true);

                //add the coursePanel to the JFrame
                topFrame.add(coursePanel);

                //takes the course selected by the user and store it in a string
                String key = (String)classSelector.getSelectedItem();

                //set the title of the panel to the course selected by the user
                coursePanel.setBorder(BorderFactory.createTitledBorder(key));

                ArrayList<Double> points = new ArrayList<Double>();

                Course current;
                //loop through the hash map storing all of our course data. Checking all semesters and all blocks
                for (String semester: data.getSemesters()) {
                    for (int i=0;i < blockList.length;i++) {

                        //if the hash map contains a course that matches the semester and block. Add it to the coursePanel
                        try {
                            current = data.getAllData().get(semester).get(key).get(blockList[i]);
                            if(current!=null){
                                coursePanel.add(new JLabel("Block " + current.getBlockNumber() + " " + data.convertSemester(semester)));
                                points.add((double)current.minPoints);
                            }
                            coursePanel.add(current);

                        } catch (NullPointerException r) {}
                    }
                    double sumPoints = 0;
                    for (double minPoint: points){
                        sumPoints+=minPoint;
                    }
                    double average = sumPoints/(points.size());
                    avg.setText("Average points required: " + String.valueOf(average));
                    coursePanel.add(avg);
                    coursePanel.add(doneButton);
                }
                coursePanel.setVisible(true);
            }
        });

        //This button takes the user back to the startPanel(the first page);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coursePanel.setVisible(false);
                startPanel.setVisible(true);
                coursePanel.removeAll();
            }
        });

        topFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.display();
    }
}
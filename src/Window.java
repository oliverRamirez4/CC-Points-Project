import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Window {
    Data data;

    JFrame topFrame;
    JPanel startPanel, coursePanel;
    JLabel CCLogo,avg;
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

        //Create the border that holds the title for CoursePanel



        //create a JLabel containing the CC Logo
        CCLogo = new JLabel(new ImageIcon("src/CC-Logo-Stacked.png"));
        avg = new JLabel();

        //need for using dropDown menu
        //courses=data.getCourseList();
        classList = data.getCourseList();
        //classList = data.getAllData().get("2023S").keySet().toArray(String[]::new);
        Arrays.sort(classList);
        System.out.println(classList.length);

        blockList = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "H", "5-6", "5-7", "5-8", "6-7", "6-8", "7-8", "1-2", "1-3", "1-4", "2-3", "2-4", "3-4" };

        classSelector=new JComboBox(classList);

        goButton=new JButton("go");
        doneButton = new JButton("Done");

    }



    public void display(){
        topFrame.setSize(600, 300);
        topFrame.setVisible(true);
        topFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topFrame.setTitle("Colorado College Point Calculator");

        startPanel.setBackground(Color.LIGHT_GRAY);
        startPanel.setLayout(new BoxLayout(startPanel,BoxLayout.X_AXIS));
        topFrame.add(startPanel);

        startPanel.add(CCLogo);

        //give the Course Panel a box layout manager
        coursePanel.setLayout(new BoxLayout(coursePanel,BoxLayout.PAGE_AXIS));

        startPanel.add(classSelector);

        startPanel.add(goButton);
        System.out.print(Arrays.toString(courses));
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPanel.setVisible(false);
                //coursePanel.setLayout(new BoxLayout(coursePanel,BoxLayout.Y_AXIS));
                topFrame.add(coursePanel);
                coursePanel.setVisible(true);

                String key = (String)classSelector.getSelectedItem();
                coursePanel.setBorder(BorderFactory.createTitledBorder(key));
                String[] semesters= data.getSemesters();
                System.out.println(Arrays.toString(semesters));
                Course current;
                ArrayList<Double> points = new ArrayList<Double>();
                for (String semester: semesters) {
                    for (int i=0;i < blockList.length;i++) {
                        try {
                            current = data.getAllData().get(semester).get(key).get(blockList[i]);
                            if(current!=null){
                                double min = current.minPoints;
                                points.add(min);
                                coursePanel.add(new JLabel(semester));
                            }
                            coursePanel.add(current);

                        } catch (NullPointerException t) {
                        }

                    }
                    double sumPoints = 0;
                    for (double minPoint: points){
                        sumPoints+=minPoint;
                    }
                    double average = sumPoints/(points.size());
                    avg.setText("Average points required: " + String.valueOf(average));
                    coursePanel.add(avg);
                    coursePanel.add(doneButton);
                    coursePanel.setVisible(true);
                }

                coursePanel.setVisible(true);

            }
        });
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
        Data data = new Data();
        System.out.println(data.getAllData().get("2023S").get("SP101").keySet());
    }

}
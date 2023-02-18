import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;

public class Window {
    Data data;
    JFrame topFrame;
    JPanel startPanel;
    Course coursePanel;
    JLabel CCLogo, printData;
    String[] classList;
    JComboBox classSelector;
    JButton goButton;

    String [] courses;

    HashMap<String, HashMap<String, HashMap<String, Course>>> allData;

    public Window(){
        //make Data object
        data=new Data();
        allData = data.getAllData();


        //what course does user select (the key)
        //could get from actionListener
        String courseID = "CP125"; //getUserInput();


        //create the JFrame
        topFrame = new JFrame();

        //create the JPanel and add it to the screen
        startPanel = new JPanel();


        //create the JPanel that will go to course information
        coursePanel=new Course();

        //create a JLabel containing the CC Logo
        CCLogo = new JLabel(new ImageIcon("src/CC-Logo-Stacked.png"));

        //need for using dropDown menu
        //courses=data.getCourseList();
        classList = allData.keySet().toArray(String[]::new);
        Arrays.sort(classList);

        classSelector=new JComboBox(classList);

        goButton=new JButton("go");

    }



    public void display(){
        topFrame.setSize(600, 200);
        topFrame.setVisible(true);
        topFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topFrame.setTitle("Colorado College Point Calculator");

        startPanel.setBackground(Color.LIGHT_GRAY);
        startPanel.setLayout(new BoxLayout(startPanel,BoxLayout.X_AXIS));
        topFrame.add(startPanel);

        coursePanel.setBackground(Color.LIGHT_GRAY);
        coursePanel.setLayout(new BoxLayout(coursePanel,BoxLayout.PAGE_AXIS));

        startPanel.add(CCLogo);

        startPanel.add(classSelector);

        startPanel.add(goButton);
        System.out.print(Arrays.toString(courses));
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPanel.setVisible(false);
                topFrame.add(coursePanel);
                coursePanel.setVisible(true);
                String key = (String)classSelector.getSelectedItem();
                String semester = "2023S";
                HashMap<String, HashMap<String, Course>> value = allData.get(key);
                Course current = value.get(key).get(semester);
                String thisData = data.getPrintData();
                printData = new JLabel(thisData);
                topFrame.add(current);
                coursePanel.add(printData);
                coursePanel.add(CCLogo);


            }
        });

        topFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.display();
    }

}
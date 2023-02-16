import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

public class Window {
    Data data;
    JFrame topFrame;
    JPanel startPanel;
    Course coursePanel;
    JLabel CCLogo;
    String[] classList;
    JComboBox classSelector;
    JButton goButton;

    String [] courses;

    public Window() throws IOException, InvalidFormatException {

        //create the JFrame
        topFrame = new JFrame();

        //create the JPanel and add it to the screen
        startPanel = new JPanel();

        //create the JPanel that will go to course information
        coursePanel=new Course();

        //create a JLabel containing the CC Logo
        CCLogo = new JLabel(new ImageIcon("src/CC-Logo-Stacked.png"));
        String url = "./src/CP222 - Projet/2020-11-19 - Course Demand and Point Distribution.xlsx", courseID, courseBlock; int block;
        data = new Data(url);
        for (int i = 0; i < data.workbook.getNumberOfSheets(); i++) {
            courseID = data.getCourseID(i);
            courseBlock = data.getCourseBlock(i);
            //FileOutputStream fileOut = new FileOutputStream("src/usableData/2021S/" + courseID + courseBlock + ".ser");
            //ObjectOutputStream out = new ObjectOutputStream(fileOut);
            try {
                data.addToMinMaxPointsData(i);
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        }
        courses = data.getCourseList(data);
        classSelector = new JComboBox(courses);


        //this combo box will have a drop down menu of all of the classes


        //go Button to request info of course
        goButton = new JButton("go");


    }

    void Display(){
        topFrame.setSize(400, 600);
        topFrame.setVisible(true);
        topFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topFrame.setTitle("Colorado College Point Calculator");

        startPanel.setBackground(Color.LIGHT_GRAY);
        startPanel.setLayout(new BoxLayout(startPanel,BoxLayout.PAGE_AXIS));
        topFrame.add(startPanel);

        coursePanel.setBackground(Color.LIGHT_GRAY);

        startPanel.add(CCLogo);

        startPanel.add(classSelector);

        startPanel.add(goButton);
        System.out.print(Arrays.toString(courses));
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPanel.setVisible(false);
                topFrame.add(coursePanel);

            }
        });

        topFrame.setVisible(true);
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {

        Window w = new Window();
        w.Display();
    }
}
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Window {

    JFrame topFrame;

    public Window() {

        //create the JFrame
        topFrame = new JFrame();
        topFrame.setSize(400, 600);
        topFrame.setVisible(true);
        topFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topFrame.setTitle("Colorado College Point Calculator");

        //create the JPanel and add it to the screen
        JPanel startPanel = new JPanel();
        startPanel.setBackground(Color.LIGHT_GRAY);
        startPanel.setLayout(new BoxLayout(startPanel,BoxLayout.PAGE_AXIS));
        topFrame.add(startPanel);


        //create the JPanel that will go to course information
        Course coursePanel=new Course();
        coursePanel.setBackground(Color.LIGHT_GRAY);


        //create a JLabel containing the CC Logo



        JLabel CCLogo = new JLabel(new ImageIcon("src/CC-Logo-Stacked.png"));
        startPanel.add(CCLogo);















        //this combo box will have a drop down menu of all of the classes
        String[] classList = new String[]{"choose your course","cp122","ap123","dp124"};//place filler until we have all the classes
        JComboBox classSelector = new JComboBox(classList);
        startPanel.add(classSelector);

        JButton goButton = new JButton("go");
        startPanel.add(goButton);

        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startPanel.setVisible(false);
                topFrame.add(coursePanel);

            }
        });

        topFrame.setVisible(true);












    }

    public static void main(String[] args) {
        Window w = new Window();
    }
}
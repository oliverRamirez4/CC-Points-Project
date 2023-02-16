import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Window {
    JFrame topFrame;
    JPanel startPanel;
    Course coursePanel;
    JLabel CCLogo;
    String[] classList;
    JComboBox classSelector;
    JButton goButton;

    public Window() {

        //create the JFrame
        topFrame = new JFrame();

        //create the JPanel and add it to the screen
        startPanel = new JPanel();

        //create the JPanel that will go to course information
        coursePanel=new Course();

        //create a JLabel containing the CC Logo
        CCLogo = new JLabel(new ImageIcon("src/CC-Logo-Stacked.png"));

        //this combo box will have a drop down menu of all of the classes
        classList = new String[]{"choose your course","cp122","ap123","dp124"};//place filler until we have all the classes
        classSelector = new JComboBox(classList);

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
        w.Display();
    }
}
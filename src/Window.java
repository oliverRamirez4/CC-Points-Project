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
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        topFrame.add(panel);




        //this combo box will have a drop down menu of all of the classes
        String[] classList = new String[10];//place filler until we have all the classes
        classList[0]="Hi this is the box";
        JComboBox classSelector = new JComboBox(classList);

        panel.add(classSelector);

        topFrame.setVisible(true);












    }

    public static void main(String[] args) {
        Window w = new Window();
    }
}
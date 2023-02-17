import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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

    public Window(){
        //make Data object
        data=new Data();


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
        classList = data.getAllData().keySet().toArray(String[]::new);
        Arrays.sort(classList);

        classSelector=new JComboBox(classList);

        goButton=new JButton("go");

    }



    public void display(){
        topFrame.setSize(400, 600);
        topFrame.setVisible(true);
        topFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topFrame.setTitle("Colorado College Point Calculator");

        startPanel.setBackground(Color.LIGHT_GRAY);
        startPanel.setLayout(new BoxLayout(startPanel,BoxLayout.Y_AXIS));
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
                String key = (String)classSelector.getSelectedItem();
                String semester = "2023S";
                Course current = data.getAllData().get(key).get(semester);
                topFrame.add(current);

            }
        });

        topFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.display();
    }

}
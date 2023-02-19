import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class Window {
    Data data;

    JFrame topFrame;
    JPanel startPanel, coursePanel;
    JLabel CCLogo;
    String[] classList;
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

        //create a JLabel containing the CC Logo
        CCLogo = new JLabel(new ImageIcon("src/CC-Logo-Stacked.png"));

        //need for using dropDown menu
        //courses=data.getCourseList();
        classList = data.getAllData().keySet().toArray(String[]::new);
        Arrays.sort(classList);

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
                //String semester = "2023S";
                String[] semesters= data.getAllData().get(key).keySet().toArray(new String[data.getAllData().keySet().size()]);
                System.out.println(Arrays.toString(semesters));
                Course current= new Course();
                for (String semester: semesters) {
                    current = data.getAllData().get(key).get(semester);
                    coursePanel.add(current);
                    coursePanel.add(new JLabel(semester));
                    coursePanel.add(doneButton);
                    System.out.print(current);
                }

                coursePanel.setVisible(true);

            }
        });
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coursePanel.setVisible(false);
                startPanel.setVisible(true);
            }
        });

        topFrame.setVisible(true);
    }

    public static void main(String[] args) {
        Window w = new Window();
        w.display();
    }

}
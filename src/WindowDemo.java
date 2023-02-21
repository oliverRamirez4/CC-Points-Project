import javax.swing.*;
import java.awt.*;

    public class WindowDemo {

        JFrame jf;
        JLabel gender,hobbies,name_label,rollno_label,marks_label,city_label,address_label;
        JTextField name_field,rollno_field,marks_field;
        JRadioButton male,female;
        ButtonGroup bg;
        JCheckBox photography,music,sketching,coding;
        JComboBox city_combo;
        JTextArea adress_textarea;
        JButton save, exit;
        JMenuBar mbar;
        JMenu file,edit,help;
        JMenuItem open,save_item,edit_item,close,cut,copy,paste,find,replace,help_content,about,updates;


        public WindowDemo()  //constructor
        {

            jf = new JFrame("Student Information");
            name_label = new JLabel("Student's Name");
            name_field = new JTextField();
            rollno_label = new JLabel("Student's Roll Number");
            rollno_field = new JTextField();
            marks_label = new JLabel("Student's Total Marks Achieved");
            marks_field = new JTextField();
            gender = new JLabel("Gender");
            male = new JRadioButton("Male");
            female = new JRadioButton("Female");
            bg = new ButtonGroup();
            hobbies = new JLabel("Hobbies");
            photography = new JCheckBox("Photography");
            music = new JCheckBox("Music");
            coding = new JCheckBox("Coding");
            sketching = new JCheckBox("Sketching");
            city_label = new JLabel("City");
            city_combo = new JComboBox();
            address_label = new JLabel("Residential Address");
            adress_textarea = new JTextArea();
            save = new JButton("Save");
            exit = new JButton("Exit");
            mbar = new JMenuBar();
            file = new JMenu("File");
            edit = new JMenu("Edit");
            help = new JMenu("Help");
            open = new JMenuItem("open");
            save_item = new JMenuItem("Save");
            edit_item = new JMenuItem("Edit");
            close = new JMenuItem("Close");
            cut = new JMenuItem("Cut");
            copy = new JMenuItem("Copy");
            paste = new JMenuItem("Paste");
            find = new JMenuItem("Find");
            replace = new JMenuItem("Replace");
            about = new JMenuItem("About");
            updates = new JMenuItem("Check for Updates");
            help_content = new JMenuItem("Help Content");

        }

        void Display()
        {
            jf.setSize(1000, 700);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setLayout(null);
            jf.getContentPane().setBackground( Color.LIGHT_GRAY );

            name_label.setBounds(50, 50, 150, 20);
            name_field.setBounds(300, 50, 200, 20);
            rollno_label.setBounds(50, 100, 150, 20);
            rollno_field.setBounds(300, 100, 200, 20);
            marks_label.setBounds(50, 150, 200, 20);
            marks_field.setBounds(300, 150, 200, 20);
            gender.setBounds(50, 200, 100, 20);
            male.setBounds(300, 200, 80, 20);
            female.setBounds(400, 200, 80, 20);
            hobbies.setBounds(50, 250, 80, 20);
            photography.setBounds(300, 250, 100, 20);
            music.setBounds(420, 250, 80, 20);
            sketching.setBounds(500, 250, 100, 20);
            coding.setBounds(600, 250, 80, 20);
            city_label.setBounds(50, 300, 100, 20);
            city_combo.setBounds(300, 300, 100, 20);
            address_label.setBounds(50, 350, 200, 20);
            adress_textarea.setBounds(300, 350, 300, 100);
            save.setBounds(300, 500, 100, 50);
            exit.setBounds(600, 500, 100, 50);

            bg.add(male);
            bg.add(female);

            city_combo.addItem("Select City");
            city_combo.addItem("Chandigarh");
            city_combo.addItem("Kurali");
            city_combo.addItem("Mohali");
            city_combo.addItem("Panchkula");

            file.add(open);
            file.add(save_item);
            file.add(edit_item);
            file.add(close);
            edit.add(cut);
            edit.add(copy);
            edit.add(paste);
            edit.add(find);
            edit.add(replace);
            help.add(about);
            help.add(help_content);
            help.add(updates);
            mbar.add(file);
            mbar.add(edit);
            mbar.add(help);

            jf.add(name_label);
            jf.add(name_field);
            jf.add(rollno_label);
            jf.add(rollno_field);
            jf.add(marks_label);
            jf.add(marks_field);
            jf.add(gender);
            jf.add(male);
            jf.add(female);
            jf.add(hobbies);
            jf.add(music);
            jf.add(photography);
            jf.add(sketching);
            jf.add(coding);
            jf.add(city_label);
            jf.add(city_combo);
            jf.add(address_label);
            jf.add(adress_textarea);
            jf.add(save);
            jf.add(exit);

            jf.setJMenuBar(mbar);

            jf.setVisible(true);


        }

        public static void main(String[] args) {
            new WindowDemo().Display();
        }

    }

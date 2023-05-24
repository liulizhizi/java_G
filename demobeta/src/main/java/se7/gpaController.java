package se7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

public class gpaController {

    @FXML
    private TextArea Date;

    @FXML
    private TextArea Field;

    @FXML
    private TextArea GPA;

    @FXML
    private AnchorPane Grade;

    @FXML
    private Button Save1;

    @FXML
    private TextArea credit;


    @FXML
    private ChoiceBox<?> Subject;

    public static ArrayList<String> dateList = new ArrayList<String>();
    public static ArrayList<String> subjectList = new ArrayList<String>();
    public static ArrayList<String> gpaList = new ArrayList<String>();
    public static ArrayList<String> fieldList = new ArrayList<String>();
    public static ArrayList<String> creditList = new ArrayList<String>();
    private String date;
    private String subject;
    private String gpa;
    private String field;
    public String cre;

    public static void readFile() {
        String pathname = "gpa.txt";
        int a;
        int b;
        int c;
        int d;
        int ea;
        try (InputStream reader = achievementController.class.getResourceAsStream(pathname);
            BufferedReader br = new BufferedReader(new InputStreamReader(reader)) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            dateList.clear();
            subjectList.clear();
            gpaList.clear();
            fieldList.clear();
            creditList.clear();
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                a = line.indexOf("[date]:");
                b = line.indexOf("[subject]:");
                c = line.indexOf("[gpa]:");
                d = line.indexOf("[field]:");
                ea = line.indexOf("[credit]:");
                dateList.add(line.substring(a + 7, b));
                subjectList.add(line.substring(b + 10, c));
                gpaList.add(line.substring(c + 6, d));
                fieldList.add(line.substring(d + 8, ea));
                creditList.add(line.substring(ea + 9));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    public static void writeFile() {
        try (InputStream reader = achievementController.class.getResourceAsStream("gpa.txt");
            PrintWriter writeName = new PrintWriter(String.valueOf(new InputStreamReader(reader)))// 相对路径，如果没有则要建立一个新的output.txt文件
        ) {
            for (int i = 0; i < dateList.size(); i++)
                writeName.write("[date]:" + dateList.get(i) + "[subject]:" + subjectList.get(i) + "[gpa]:" + gpaList.get(i) + "[field]:" + fieldList.get(i) + "[credit]:" + creditList.get(i) + "\r");
            writeName.close();
            writeName.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void saveTextGPA(ActionEvent event) {
        if (Date.getText().isEmpty() || GPA.getText().isEmpty() || Field.getText().isEmpty() || credit.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Input cannot be empty!", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isNumber(GPA.getText())) {
            JOptionPane.showMessageDialog(null, "Wrong GPA", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        float gp = 0;
        gp = Float.parseFloat(GPA.getText());
        if (gp > 4 || gp < 0) {
            JOptionPane.showMessageDialog(null, "Wrong GPA", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isNumber(credit.getText())) {
            JOptionPane.showMessageDialog(null, "Wrong CREDIT", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        float cred = 0;
        cred = Float.parseFloat(credit.getText());
        if (cred > 5 || cred < 0) {
            JOptionPane.showMessageDialog(null, "Wrong Credit", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        date = Date.getText();
        subject = (String) Subject.getValue();
        gpa = GPA.getText();
        field = Field.getText();
        cre = credit.getText();
        readFile();
        dateList.add(date);
        subjectList.add(subject);
        gpaList.add(gpa);
        fieldList.add(field);
        creditList.add(cre);
        writeFile();
        JOptionPane.showMessageDialog(null, "Successfully saved", "Success!!", JOptionPane.PLAIN_MESSAGE);
        Date.setText("");
        GPA.setText("");
        Field.setText("");
        credit.setText("");
    }

    @FXML
    void choiceSubject(ActionEvent event) {
        String sub=(String)Subject.getValue();
        if (sub.equals("Linear Algebra")) {
            Date.setText("20200202");
            credit.setText("3");
        }
        if (sub.equals("Advanced Mathematics")) {
            Date.setText("20200203");
            credit.setText("3");
        }
        if (sub.equals("C programming")) {
            Date.setText("20200310");
            credit.setText("2");
        }
        if (sub.equals("Electronic Fundamentals")) {
            Date.setText("20200205");
            credit.setText("2");
        }
        if (sub.equals("Java programming")) {
            Date.setText("20220206");
            credit.setText("3");
        }
        if (sub.equals("Probability Theory")) {
            Date.setText("20220207");
            credit.setText("4");
        }if (sub.equals("PDP")) {
            Date.setText("20200203");
            credit.setText("1");
        }if (sub.equals("Signal and System")) {
            Date.setText("20210910");
            credit.setText("5");
        }if (sub.equals("DSP")) {
            Date.setText("202200311");
            credit.setText("4");
        }if (sub.equals("Engineering Mathematics")) {
            Date.setText("20200210");
            credit.setText("5");
        }if (sub.equals("Academic Conversation")) {
            Date.setText("20200203");
            credit.setText("1");
        }if (sub.equals("Multimedia Fundamentals")) {
            Date.setText("20220923");
            credit.setText("3");
        }if (sub.equals("Advanced Transform")) {
            Date.setText("20220925");
            credit.setText("4");
        }

    }
}


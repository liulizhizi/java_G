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
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;

public class achievementController {


    @FXML
    private TextArea Date;

    @FXML
    private TextArea Field;

    @FXML
    private TextArea Title;


    static ArrayList<String> dateList =new ArrayList<String>();
    static ArrayList <String>titleList =new ArrayList<String>();
    static ArrayList <String>fieldList =new ArrayList<String>();
    private String date;
    private String title;
    private String field;

    public static void readFile() {
        String pathname = "achievement.txt";
        int a;
        int b;
//        int c;
        int d;
        try (InputStream reader = achievementController.class.getResourceAsStream(pathname);
            BufferedReader br = new BufferedReader(new InputStreamReader(reader)) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            dateList.clear();
            titleList.clear();
//            gpaList.clear();
            fieldList.clear();
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                a=line.indexOf("[date]:");
                b=line.indexOf("[title]:");
//                c=line.indexOf("[gpa]:");
                d=line.indexOf("[field]:");
                dateList.add(line.substring(a+7,b));
                titleList.add(line.substring(b+8,d));
//                gpaList.add(line.substring(c+6,d));
                fieldList.add(line.substring(d+8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean legallyDate(String date) {

//将String类型的日期转换为Integer类型进行判断
        int dateInt = Integer.parseInt(date);
//取出日期中的年月日分别进行判断
        int year = dateInt / 10000;
        int month = (dateInt % 10000) / 100;
        int day = dateInt % 100;

//定义一个合法月份的天数数组，校验天数是否合法
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
//由于平年二月28天，闰年二月29天，需额外赋值
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; //闰年
        } else {
            arr[1] = 28; //平年
        }

//校验月份是否合法，0<month<13
        if (month > 0 && month < 13) {
            if (day <= arr[month - 1] && day > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

//    if (Date.getText().length() != 8) {
//        JOptionPane.showMessageDialog(null, "Wrong Date Format", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
//        return;
//    }

    public static void writeFile() {
        try(InputStream reader = achievementController.class.getResourceAsStream("achievement.txt");
            PrintWriter writeName = new PrintWriter(String.valueOf(new InputStreamReader(reader)))// 相对路径，如果没有则要建立一个新的output.txt文件
        ){  for(int i=0;i<dateList.size();i++)
            writeName.write("[date]:"+dateList.get(i)+"[title]:"+titleList.get(i)+"[field]:"+fieldList.get(i)+"\r");
            writeName.flush();
            writeName.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void saveText(ActionEvent event) {
        if(Date.getText().isEmpty()||Title.getText().isEmpty()||Field.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Input cannot be empty", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ((!isNumber(Date.getText()))||(!legallyDate(Date.getText()))||(Date.getText().length() != 8)){
            JOptionPane.showMessageDialog(null, "Wrong Date Format", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        date=Date.getText();
        title=Title.getText();
        field=Field.getText();
        readFile();
        dateList.add(date);
        titleList.add(title);
        fieldList.add(field);
        writeFile();
        JOptionPane.showMessageDialog(null, "Successfully saved","Success!!",JOptionPane.PLAIN_MESSAGE);
        Date.setText("");
        Title.setText("");
        Field.setText("");
    }

}

package se7;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;


public class achievementReviewController implements Initializable{


    @FXML
    private TextArea Date;

    @FXML
    private TextArea Field;

    @FXML
    private TextArea Title;


    @FXML
    private Button Next;


    @FXML
    private Button Save;

//    @FXML
//    private TextArea Subject;
    private  int ad=0;

    public void initialize(URL location, ResourceBundle resources) {
        achievementController.readFile();
        Date.setText(achievementController.dateList.get(ad));
        Title.setText(achievementController.titleList.get(ad));
//        GPA.setText(gpaController.gpaList.get(ad));
        Field.setText(achievementController.fieldList.get(ad));
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

    @FXML
    void nextRecord(ActionEvent event) {
        achievementController.readFile();
        ad++;
        if(ad==achievementController.dateList.size()){
            JOptionPane.showMessageDialog(null, "last record!","wrong!!!!!",JOptionPane.ERROR_MESSAGE);
            ad--;
            return;
        }
        Date.setText(achievementController.dateList.get(ad));
        Title.setText(achievementController.titleList.get(ad));
        Field.setText(achievementController.fieldList.get(ad));
    }

    @FXML
    void saveText(ActionEvent event) {
        if(Date.getText().isEmpty()||Title.getText().isEmpty()||Field.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Input cannot be empty!","wrong!!!!!",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if ((!isNumber(Date.getText()))||(!legallyDate(Date.getText()))||(Date.getText().length() != 8)){
            JOptionPane.showMessageDialog(null, "Wrong Date Format", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        achievementController.dateList.set(ad,Date.getText());
        achievementController.titleList.set(ad,Title.getText());
        achievementController.fieldList.set(ad,Field.getText());
        achievementController.writeFile();
        JOptionPane.showMessageDialog(null, "Successfully saved","Success!!",JOptionPane.PLAIN_MESSAGE);
    }

    @FXML
    void previousRecord(ActionEvent event) {
        achievementController.readFile();
        ad--;
        if(ad==-1){
            JOptionPane.showMessageDialog(null, "first record!","wrong!!!!!",JOptionPane.ERROR_MESSAGE);
            ad++;
            return;
        }
        Date.setText(achievementController.dateList.get(ad));
        Title.setText(achievementController.titleList.get(ad));
        Field.setText(achievementController.fieldList.get(ad));
    }

}

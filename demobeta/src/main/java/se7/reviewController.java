package se7;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

public class reviewController implements Initializable {

    @FXML
    private TextArea Date;

    @FXML
    private TextArea Field;

    @FXML
    private TextArea GPA;

    @FXML
    private AnchorPane Grade;

    @FXML
    private Button Last1;

    @FXML
    private Button Next;

    @FXML
    private Button Save;

    @FXML
    private TextArea Subject;

    @FXML
    private TextArea ttoalGPA;

    @FXML
    private TextArea Credit;

    private  int ad=0;

    public void initialize(URL location, ResourceBundle resources) {
        gpaController.readFile();
        Date.setText(gpaController.dateList.get(ad));
        Subject.setText(gpaController.subjectList.get(ad));
        GPA.setText(gpaController.gpaList.get(ad));
        Field.setText(gpaController.fieldList.get(ad));
        Credit.setText(gpaController.creditList.get(ad));
        float totalcredit = 0;
        for(int ii=0;ii<gpaController.dateList.size();ii++){
            totalcredit = totalcredit + Float.parseFloat(gpaController.creditList.get(ii));
        }
        float totalgpa = 0;
        float totalweightedgpa = 0;
        for(int iii=0;iii<gpaController.dateList.size();iii++){
            float onegpa = Float.parseFloat(gpaController.gpaList.get(iii));
            float onecredit = Float.parseFloat(gpaController.creditList.get(iii));
            totalweightedgpa = totalweightedgpa+onegpa*onecredit;
        }
        totalgpa = totalgpa + totalweightedgpa/totalcredit;
        String total = Float.toString(totalgpa);
        ttoalGPA.setText(total);
    }

    @FXML
    void nextRecord(ActionEvent event) {
        gpaController.readFile();
        ad++;
        if(ad==gpaController.dateList.size()){
            JOptionPane.showMessageDialog(null, "last record!","wrong!!!!!",JOptionPane.ERROR_MESSAGE);
            ad--;
            return;
        }
        Date.setText(gpaController.dateList.get(ad));
        Subject.setText(gpaController.subjectList.get(ad));
        GPA.setText(gpaController.gpaList.get(ad));
        Field.setText(gpaController.fieldList.get(ad));
        Credit.setText(gpaController.creditList.get(ad));
    }

    @FXML
    void saveText(ActionEvent event) {
        if(Date.getText().isEmpty()||Subject.getText().isEmpty()||GPA.getText().isEmpty()||Field.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Input cannot be empty!","wrong!!!!!",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!gpaController.isNumber(GPA.getText())) {
            JOptionPane.showMessageDialog(null, "Wrong GPA", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        float gp = 0;
        gp = Float.parseFloat(GPA.getText());
        if (gp > 4 || gp < 0) {
            JOptionPane.showMessageDialog(null, "Wrong GPA", "wrong!!!!!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        gpaController.dateList.set(ad,Date.getText());
        gpaController.subjectList.set(ad,Subject.getText());
        gpaController.gpaList.set(ad,GPA.getText());
        gpaController.fieldList.set(ad,Field.getText());
        gpaController.creditList.set(ad,Credit.getText());
        gpaController.writeFile();
        JOptionPane.showMessageDialog(null, "Successfully saved","Success!!",JOptionPane.PLAIN_MESSAGE);
        float totalcredit = 0;
        for(int ii=0;ii<gpaController.dateList.size();ii++){
            totalcredit = totalcredit + Float.parseFloat(gpaController.creditList.get(ii));
        }
        float totalgpa = 0;
        float totalweightedgpa = 0;
        for(int iii=0;iii<gpaController.dateList.size();iii++){
            float onegpa = Float.parseFloat(gpaController.gpaList.get(iii));
            float onecredit = Float.parseFloat(gpaController.creditList.get(iii));
            totalweightedgpa = totalweightedgpa+onegpa*onecredit;
        }
        totalgpa = totalgpa + totalweightedgpa/totalcredit;
        String total = Float.toString(totalgpa);
        ttoalGPA.setText(total);
    }

    @FXML
    void lastText(ActionEvent event) {
        gpaController.readFile();
        ad--;
        if(ad==-1){
            JOptionPane.showMessageDialog(null, "first record!","wrong!!!!!",JOptionPane.ERROR_MESSAGE);
            ad++;
            return;
        }
        Date.setText(gpaController.dateList.get(ad));
        Subject.setText(gpaController.subjectList.get(ad));
        GPA.setText(gpaController.gpaList.get(ad));
        Field.setText(gpaController.fieldList.get(ad));
        Credit.setText(gpaController.creditList.get(ad));
    }


}

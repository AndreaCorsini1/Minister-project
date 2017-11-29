/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicazione;


import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Cassa
 */
public class EditDialogController implements Initializable {

    
     private Stage dialogStage;
     private Iscritto iscritto;
     
     String id;
    private Connection c;
    
      private boolean okClicked = false;
        
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField cognomeField;

    @FXML
    private ComboBox<String> comboBoxSesso;

    @FXML
    private TextField luogoField;

    @FXML
    private TextField comuneField;

    @FXML
    private TextField indirizzoField;

    @FXML
    private TextField capField;

    @FXML
    private ComboBox<String> comboBoxProvincia;

    @FXML
    private ComboBox<String> comboBoxRegione;

    @FXML
    private TextField cellulareField;

    @FXML
    private TextField mailField;
    
    @FXML
    private DatePicker datePicker;


    
    LocalDate dataNascita;


     
    String comple;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        c = TestController.connection.getConnection();
        
    }    
    
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param iscritto
     * @throws java.sql.SQLException
     * 
     */
    public void setIscritto(Iscritto iscritto) throws SQLException {
        this.iscritto = iscritto;
        
        id = iscritto.getID();
        String date = iscritto.getDataNascita();
        dataNascita = LocalDate.parse(date, formatter);

        nomeField.setText(iscritto.getNome());
        cognomeField.setText(iscritto.getCognome());
        comboBoxSesso.setValue(iscritto.getSesso());
        datePicker.setValue(dataNascita);
        luogoField.setText(iscritto.getLuogoNascita());
        comuneField.setText(iscritto.getComune());
        indirizzoField.setText(iscritto.getIndirizzo());
        capField.setText(iscritto.getCAP());
        comboBoxProvincia.setValue(iscritto.getProvincia());
        comboBoxRegione.setValue(iscritto.getRegione());
        cellulareField.setText(iscritto.getCellulare());
        mailField.setText(iscritto.getEmail());
    }
    
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() throws SQLException {
        if (isInputValid()) {
            
            
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/yyyy");
          
            String data = datePicker.getValue().format(formatters);
            
            /*iscritto.setNome(nomeField.getText());
            iscritto.setCognome(cognomeField.getText());
            iscritto.setSesso(comboBoxSesso.getValue());
            iscritto.setDataNascita(data);
            iscritto.setLuogoNascita(luogoField.getText());
            iscritto.setComune(comuneField.getText());
            iscritto.setIndirizzo(indirizzoField.getText());
            iscritto.setCAP(capField.getText());
            iscritto.setProvincia(comboBoxProvincia.getValue());
            iscritto.setRegione(comboBoxRegione.getValue());
            iscritto.setCellulare(cellulareField.getText());
            iscritto.setEmail(mailField.getText()); */

            String SQL2 = "UPDATE Iscritti SET Nome = ?, Cognome = ?, Sesso = ?, DataNascita = ?, "
                    + "LuogoNascita = ?, Comune = ?,"
                    + " Indirizzo = ?, CAP = ?, Provincia = ?, Regione = ?, "
                    + "Cellulare = ?, Mail = ? WHERE ID = ?";
            
            // set the preparedstatement parameters
            PreparedStatement ps = c.prepareStatement(SQL2);
                // set the preparedstatement parameters
                
                ps.setString(1, nomeField.getText());
                ps.setString(2, cognomeField.getText());
                ps.setString(3, comboBoxSesso.getValue());
                ps.setString(4, data);
                ps.setString(5, luogoField.getText());
                ps.setString(6, comuneField.getText());
                ps.setString(7, indirizzoField.getText());
                ps.setString(8, capField.getText());
                ps.setString(9, comboBoxProvincia.getValue());
                ps.setString(10, comboBoxRegione.getValue());
                ps.setString(11, cellulareField.getText());
                ps.setString(12, mailField.getText());
                ps.setString(13, id);
                
                // call executeUpdate to execute our sql update statement
                ps.executeUpdate();
            
    
            
            okClicked = true;
            dialogStage.close();
        }
    }

    
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
     /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (nomeField.getText() == null || nomeField.getText().length() == 0) {
            errorMessage += "Nome non valido! \n"; 
        }
        if (cognomeField.getText() == null || cognomeField.getText().length() == 0) {
            errorMessage += "Cognome non valido! \n"; 
        }
        if (comboBoxSesso.getValue() == null || comboBoxSesso.getValue().length() == 0) {
            errorMessage += "Sesso non valido!\n"; 
        }

        
        /*if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }*/

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Campi non validi!");
            alert.setHeaderText("Per favore correggi i campi non validi");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
    


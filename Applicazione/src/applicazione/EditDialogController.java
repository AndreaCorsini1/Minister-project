/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicazione;


import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    String id;
    private Connection c;
    Boolean Update = false;
    
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
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
	        c = TestController.connection.getConnection();
	        
	        ObservableList<String> sesso = FXCollections.observableArrayList("M","F");
	        comboBoxSesso.setItems(sesso);
	
	        ObservableList<String> province = FXCollections.observableArrayList();;
	        for(Province item : Province.values())
	         	province.add(item.toString());
	        comboBoxProvincia.setItems(province);
	        
	        ObservableList<String> regioni = FXCollections.observableArrayList(	"Piemonte",	"Valle d'Aosta", "Lombardia", "Trentino Alto Adige",
	        						"Veneto", "Friuli Venezia Giulia", "Liguria", "Emilia Romagna", "Toscana", "Umbria", "Marche",
	        						"Lazio", "Abruzzo", "Molise", "Campania", "Puglia", "Basilicata", "Calabria", "Sicilia", "Sardegna");
	        comboBoxRegione.setItems(regioni);
	        
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
        
	        id = iscritto.getID();
	        //Mi serve per capire se devo fare un update o insert nella tabella
	        Update = true;
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
     * Called when the user clicks the ok button.
     */
    @FXML
    private void handleOk() throws SQLException {
        if (isInputValid()) {
            
        	//executed if the user wants to update a table row
            if(Update.equals(true)){
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
		            
		            System.out.println("Update successfully done");
		            
		            okClicked = true;
		            Update = false;
		     
		    //executed only when the user wants to add a new table row
            } else {
            	DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d/MM/yyyy");   
	            String data = datePicker.getValue().format(formatters);
	
	            String SQL2 = "INSERT INTO Iscritti(Nome,Cognome,Sesso,DataNascita,LuogoNascita,Comune,Indirizzo,CAP,Provincia,Regione,Cellulare,Mail)"
	            						+ " VALUES (?, 		?, 	   ?, 		?, 		   ?, 		   ?, 		?,	  ?, 	?, 		   ?,		?,     ?); ";
	            
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
	                
	            // call executeUpdate to execute our sql update statement
	            ps.executeUpdate();
	            
	            System.out.println("Insertion successfully done");
	            
	            okClicked = true;
            }
            
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
        
        System.out.println(cellulareField.getText().length());

        if (nomeField.getText() == null || nomeField.getText().length() == 0) {
            errorMessage += "Nome non valido! \n"; 
        }
        if (cognomeField.getText() == null || cognomeField.getText().length() == 0) {
            errorMessage += "Cognome non valido! \n"; 
        }
        if (comboBoxSesso.getValue() == null || comboBoxSesso.getItems().size() == 0) {
            errorMessage += "Sesso non valido!\n"; 
        }
        if(luogoField.getText() == null || luogoField.getText().length() == 0){
        	errorMessage += "Luogo di nascita non valido\n";
        }
        if(cellulareField.getText() == null || (cellulareField.getText().length() <= 8 && cellulareField.getText().length() > 11)){
        	errorMessage += "Numero di cellulare non valido\n";
        }
        
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
    


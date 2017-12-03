package applicazione;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DatabaseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane dbAnchorPane;

    @FXML
    private TableView<Iscritto> tableIscritti;

    @FXML
    private TableColumn<Iscritto, String> ColumnID;

    @FXML
    private TableColumn<Iscritto, String> columnName;

    @FXML
    private TableColumn<Iscritto, String> columnSurname;

    @FXML
    private TableColumn<Iscritto, String> columnSesso;

    @FXML
    private TableColumn<Iscritto, String> columnNascita;

    @FXML
    private TableColumn<Iscritto, String> columnLuogo;

    @FXML
    private TableColumn<Iscritto, String> columnComune;

    @FXML
    private TableColumn<Iscritto, String> columnIndirizzo;

    @FXML
    private TableColumn<Iscritto, String> columnCap;

    @FXML
    private TableColumn<Iscritto, String> columnProvincia;

    @FXML
    private TableColumn<Iscritto, String> columnRegione;

    @FXML
    private TableColumn<Iscritto, String> columnCellulare;

    @FXML
    private TableColumn<Iscritto, String> columnMail;

    @FXML
    private TextField txtFieldSearch;
    
    @FXML
    private JFXButton btnModifica;

    @FXML
    private JFXButton btnAggiungi;

    @FXML
    private JFXButton btnRimuovi;
    
    private String query;
    
    private ObservableList<Iscritto> ord = FXCollections.observableArrayList();
    
    private PreparedStatement pst;
    
    private ResultSet rs;
       
    private Connection c;
   
    
    @FXML
    void initialize() {
        //		richiama la connessione al database creata nella classe LoginController		
		c = TestController.connection.getConnection();
		
		ColumnID.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("ID"));
		columnName.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("Nome"));
		columnSurname.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("Cognome"));
		columnSesso.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("Sesso"));
		columnNascita.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("DataNascita"));
		columnLuogo.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("LuogoNascita"));
		columnComune.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("Comune"));
		columnIndirizzo.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("Indirizzo"));
		columnCap.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("CAP"));
		columnProvincia.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("Provincia"));
		columnRegione.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("Regione"));
		columnCellulare.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("Cellulare"));
		columnMail.setCellValueFactory(new PropertyValueFactory<Iscritto, String>("Email"));
		
                
        //Vincolo per gli utenti non autorizzati       
        if(TestController.persona.getPermessi().equals("Ospite")){
        	btnAggiungi.setDisable(true);
            btnModifica.setDisable(true);
            btnRimuovi.setDisable(true);
        }
            processUpdate();
    }
    

    @FXML
    void setCitta(ActionEvent event) {

    }

  
    @FXML
    void btnAggiungi(ActionEvent event) {
    	 try {
             // Load the fxml file and create a new stage for the popup dialog.
             FXMLLoader loader = new FXMLLoader();
             loader.setLocation(Main.class.getResource("EditDialog.fxml"));
             AnchorPane page = (AnchorPane) loader.load();

             // Create the dialog Stage.
             Stage dialogStage = new Stage();
             dialogStage.setTitle("Aggiungi Iscritto");
             dialogStage.initModality(Modality.WINDOW_MODAL);
             
             Scene scene = new Scene(page);
             dialogStage.setScene(scene);

             EditDialogController controller = loader.getController();
             controller.setDialogStage(dialogStage);

             // Show the dialog and wait until the user closes it
             dialogStage.showAndWait();

         } catch (IOException e) {
             e.printStackTrace();
         }
    	 
    	 //Update the view
         processUpdate();

    }

    
    @FXML
    void btnRimuovi(ActionEvent event) {
        Iscritto selectedIscritto = tableIscritti.getSelectionModel().getSelectedItem();
        
        try{
	        String SQL = "DELETE FROM Iscritti WHERE ID=" + selectedIscritto.getID();
	        PreparedStatement ps = c.prepareStatement(SQL);
	        
	        ps.executeUpdate();
            System.out.println("Successfully delete");
            
       	    //Update the view
            processUpdate();

        } catch(Exception e){
        	e.printStackTrace();
        }

    }
    
    
    @FXML
    private void btnModifica() throws SQLException {
        Iscritto selectedIscritto = tableIscritti.getSelectionModel().getSelectedItem();
        
        if (selectedIscritto != null) {
            
            Boolean okClicked = showPersonEditDialog(selectedIscritto);
       	    //Update the view
            processUpdate();
      
        } else {
            //Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
      
    
    /**	AGGIORNA:
    *	ripulisce la tabella e l'aggiorna con i dati pescati dal database
    */
    private void processUpdate(){
    	tableIscritti.getItems().clear();
    	
    	try{
    		query= "SELECT * from Iscritti ORDER BY ID";
    		pst=c.prepareStatement(query);
    		rs=pst.executeQuery();
    		while(rs.next()){
    			ord.add(new Iscritto(rs.getString("ID"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Sesso"),
    					rs.getString("DataNascita"),rs.getString("LuogoNascita"),rs.getString("Comune"),rs.getString("Indirizzo"),
    					rs.getString("CAP"),rs.getString("Provincia"),rs.getString("Regione"),rs.getString("Cellulare"), rs.getString("Mail")));
    		}
    		
            tableIscritti.setItems(ord);
    		pst.close();
    		rs.close();
    		}catch(Exception e2){
    			System.err.println(e2);
    	} 
    }
    
    /**	SELEZIONA:
	*	restituisce l'id dell'ordine clickato sulla tabella e ne riempie i textfield
	*/
    @FXML
   private void select() {

    	ObservableList<Iscritto> selectOrd;
    	selectOrd = tableIscritti.getSelectionModel().getSelectedItems();
    	String pid=(selectOrd.get(0).getID());  //questa riga da problemi!!!
    	
    }
   
   
    /**	CERCA:
	*	ricompila la tabella in base al valore inserito in txtfieldSearch
	*/
    @FXML
    private void search(){
    	tableIscritti.getItems().clear();
    	try {
    		query= "SELECT * FROM Iscritti WHERE Nome like ? OR "
                        + "Cognome like ? OR "
                        + "DataNascita like ? OR Comune like ? "
                        + "OR Indirizzo like ? OR CAP like ? OR Provincia like ? "
                        + "OR Regione like ? OR Cellulare like ? OR Mail like ?";
    		pst=c.prepareStatement(query);
    		pst.setString(1, "%"+txtFieldSearch.getText()+"%");
                pst.setString(2, "%"+txtFieldSearch.getText()+"%");
    		pst.setString(3, "%"+txtFieldSearch.getText()+"%");
    		pst.setString(4, "%"+txtFieldSearch.getText()+"%");
                pst.setString(5, "%"+txtFieldSearch.getText()+"%");
    		pst.setString(6, "%"+txtFieldSearch.getText()+"%");
    		pst.setString(7, "%"+txtFieldSearch.getText()+"%");
                pst.setString(8, "%"+txtFieldSearch.getText()+"%");
    		pst.setString(9, "%"+txtFieldSearch.getText()+"%");
    		pst.setString(10, "%"+txtFieldSearch.getText()+"%");
                
    		rs=pst.executeQuery();
    		while(rs.next()){
    			ord.add(new Iscritto(rs.getString("ID"),rs.getString("Nome"),rs.getString("Cognome"),rs.getString("Sesso"),
    					rs.getString("DataNascita"),rs.getString("LuogoNascita"),rs.getString("Comune"),rs.getString("Indirizzo"),
    					rs.getString("CAP"),rs.getString("Provincia"),rs.getString("Regione"),rs.getString("Cellulare"),
    					rs.getString("Mail")));
    		}
            tableIscritti.setItems(ord);
    		pst.close();
    		rs.close();
    		}catch(Exception e2){
    			System.err.println(e2);
    	} 
    }
    
    /**	EDIT:
     * 	Called when the user clicks the edit button. Opens a dialog to edit
     * 	details for the selected person.
     */
     public boolean showPersonEditDialog(Iscritto iscritto) throws SQLException {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("EditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modifica Iscritto");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            EditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIscritto(iscritto);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}

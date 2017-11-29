/**
 * Sample Skeleton for 'UserView.fxml' Controller Class
 */

package applicazione;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class UserController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="dbAnchorPane"
    private AnchorPane dbAnchorPane; // Value injected by FXMLLoader

    @FXML // fx:id="tableUsers"
    private TableView<User> tableUsers; // Value injected by FXMLLoader

    @FXML // fx:id="columnId"
    private TableColumn<User, String> columnId; // Value injected by FXMLLoader

    @FXML // fx:id="columnUsername"
    private TableColumn<User, String> columnUsername; // Value injected by FXMLLoader

    @FXML // fx:id="columnPassword"
    private TableColumn<User, String> columnPassword; // Value injected by FXMLLoader

    @FXML // fx:id="columnPermessi"
    private TableColumn<User, String> columnPermessi; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldId"
    private TextField textFieldId; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldUsername"
    private TextField textFieldUsername; // Value injected by FXMLLoader
    
    @FXML
    private TextField textFieldPassword;

     @FXML
    private JFXComboBox<String> comboBoxPermessi;

    @FXML
    private JFXComboBox<String> comboBoxOperation;

    @FXML // fx:id="btnExecute"
    private Button btnExecute; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldSearch"
    private TextField txtFieldSearch; // Value injected by FXMLLoader
    
        private PreparedStatement pst;
    
    private ResultSet rs;
    
    private String query;
    
   
    
    private Connection c;
private ObservableList<User> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
       c = TestController.connection.getConnection();
		
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        columnPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        columnPermessi.setCellValueFactory(new PropertyValueFactory<>("Permessi"));
        
        //inizializza ComboBox
	    ObservableList<String> strings = FXCollections.observableArrayList("Aggiungi","Rimuovi","Modifica");
        comboBoxOperation.setItems(strings);
        
	    ObservableList<String> strings1 = FXCollections.observableArrayList("SuperAdmin","Admin","Ospite");
        comboBoxPermessi.setItems(strings1);
        
             if(TestController.persona.getPermessi().equals("Admin") || TestController.persona.getPermessi().equals("Ospite")){
        	textFieldId.setDisable(true);
        	textFieldUsername.setDisable(true);
        	textFieldPassword.setDisable(true);
        	txtFieldSearch.setDisable(true);
        	comboBoxPermessi.setDisable(true);
        	comboBoxOperation.setDisable(true);
        	btnExecute.setDisable(true);
        }
        else{
    		textFieldId.setDisable(true);
    		processUpdate();
        }
             
    }
    
    //Aggiorna la tabella
    public void processUpdate(){
    	tableUsers.getItems().clear();
    	
    	try{
    		query= "select * from User";
    		pst=c.prepareStatement(query);
    		rs=pst.executeQuery();
    		while(rs.next()){
                    data.add(new User(rs.getString("Id"),rs.getString("Username"),rs.getString("Password"),rs.getString("Permessi")));
    		}
    		
            tableUsers.setItems(data);
    		pst.close();
    		rs.close();
    		}catch(Exception e2){
    			System.err.println(e2);
    	}
    }
        
        // Seleziona la riga dalla tabella e inserisce i dati nei campi di testo a lato
    
    @FXML
    
      private void select(){
    	ObservableList<User> personaSelected;
    	personaSelected = tableUsers.getSelectionModel().getSelectedItems();
    	String pid=(personaSelected.get(0).getId());
    	try{
    		query= "select * from User WHERE ID=?";
    		pst=c.prepareStatement(query);
    		pst.setString(1, pid);
    		rs=pst.executeQuery();
    		textFieldId.setText(rs.getString("ID"));
    		textFieldUsername.setText(rs.getString("Username"));
    		textFieldPassword.setText(rs.getString("Password"));
    		comboBoxPermessi.setValue(rs.getString("Permessi"));
    		rs.close();
    		}catch(SQLException e2){
    			System.err.println(e2);
    	}
    }
      
      
      //pulisce campi di testo
    private void clearTextField(){
    	textFieldId.clear();
    	textFieldUsername.clear();
    	textFieldPassword.clear();
    	processUpdate();
    }
    
    //Cerca nella tabella i paramentri inseriti nella barra di ricerca
    @FXML
    private void search(){
    	tableUsers.getItems().clear();
    	try {
    		query= "SELECT * FROM User WHERE Username LIKE ? OR Password LIKE ? OR Permessi LIKE ?";
    		pst=c.prepareStatement(query);
    		pst.setString(1, "%"+txtFieldSearch.getText()+"%");
    		pst.setString(2, "%"+txtFieldSearch.getText()+"%");
    		pst.setString(3, "%"+txtFieldSearch.getText()+"%");
    		rs=pst.executeQuery();
    		while(rs.next()){
                    data.add(new User(rs.getString("ID"),rs.getString("Username"),rs.getString("Password"),rs.getString("Permessi")));
    		}
            tableUsers.setItems(data);
    		pst.close();
    		rs.close();
    		}catch(Exception e2){
    			System.err.println(e2);
    	}
    }
    
    
    @FXML
    void execute() {
        
        processSelection();

    }
    
    //Seleziona l'operazione da eseguire in base al valore selezionato nella comboBoxOperation
	@FXML
	private void processSelection(){
		if(comboBoxOperation.getValue().equals("Aggiungi")){
			if(textFieldUsername.getText() == null || textFieldPassword == null || comboBoxPermessi.getValue() == null){
				return;
			}else{
				processAdd();
			}
		}
		
		if(comboBoxOperation.getValue().equals("Rimuovi")){
			processDelete();
		}
		
		if(comboBoxOperation.getValue().equals("Modifica")){
			processModify();
		}
	}
        
        //Aggiunge la riga alla tabella e al database
    public void processAdd(){
    	
    	int actid=lastId()+1;
    	StringBuilder sb = new StringBuilder();
    	sb.append("");
    	sb.append(actid);
    	String aid=sb.toString();
    	
    	try {
    		query = "insert into User (ID,Username,Password,Permessi) values (?,?,?,?)";
    		pst=c.prepareStatement(query);
    		pst.setString(1, aid);
    		pst.setString(2, textFieldUsername.getText());
    		pst.setString(3, textFieldPassword.getText());
    		pst.setString(4,comboBoxPermessi.getValue());
    		pst.execute();
    		pst.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    		System.exit(0);
    	}
    	clearTextField();
}
    
    
    
    //Cancella la riga selezionata della tabella
    private void processDelete(){
    	String cancId=textFieldId.getText();
    	
    	try {
    		query = "DELETE FROM User WHERE ID=?";
    		pst=c.prepareStatement(query);
    		pst.setString(1, cancId);
    		pst.execute();
    		pst.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    		System.exit(0);
    	}
    	clearTextField();
    }
    
    
     //Modifica le informazioni riguardanti i campi aggiornati
    private void processModify(){
    	try {
    		String query = "UPDATE User SET Username = ?, Password=?, Permessi=? WHERE ID=?";
    		pst=c.prepareStatement(query);
    		pst.setString(1, textFieldUsername.getText());
    		pst.setString(2, textFieldPassword.getText());
    		pst.setString(3, comboBoxPermessi.getValue());
    		pst.setString(4, textFieldId.getText());
    		pst.execute();
    		pst.close();
    	} catch ( Exception e ) {
    		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
    		System.exit(0);
    	}
    	clearTextField();
    }
    
    
    
    
    
    
    //Vincoli
    private int lastId(){
    	int max=0;
    	int actid = 0;
    	String pass=null;
    	try{
    		query= "select * from User";
    		pst=c.prepareStatement(query);
    		rs=pst.executeQuery();
    		while(rs.next()){
    			pass=rs.getString("ID");
    			actid=Integer.parseInt(pass);
    			if(max<actid){
    				max=actid;
    			}
    		}
    		
    		pst.close();
    		rs.close();
    		}catch(Exception e2){
    			System.err.println(e2);
    	}
    	return max;
    }

    
    
    }
             
             
             
             
             
             
             
             
             
             
         
    
    


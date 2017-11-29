/**
 * Sample Skeleton for 'Test.fxml' Controller Class
 */

package applicazione;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.input.SwipeEvent;
import javafx.stage.Stage;


public class TestController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="button"
    private Button button; // Value injected by FXMLLoader
    
    @FXML
    private TextField textName;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label label;

    
    private Connection c;
    
    private Statement stmt;
    
    private String name;
    private String surname;
    
    /*Connessione che utilizzo in tutte le classi per accedere al DB*/
    public static SQLliteConnection connection;

    
    public static User persona;
    
     @FXML
    void handleButton(ActionEvent event) throws IOException {
        
        Parent home_page_parent =  FXMLLoader.load(getClass().getResource("MainAppView.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        
        
        if (isValidCredentials())
            {   
                label.setText("");
                name = textName.getText();
                surname = passwordField.getText();
                
                app_stage.hide();
                app_stage.setScene(home_page_scene);
                app_stage.show();  
                
            }
            else
            {
                textName.clear();
                passwordField.clear();
                label.setText("Username e/o Password sbagliati!");
            }
        
        
        

    }
    
     private boolean isValidCredentials()
    {
        boolean let_in = false;
        System.out.println( "SELECT * FROM User WHERE Username= " + "'" + textName.getText() + "'" 
            + " AND Password= " + "'" + passwordField.getText() + "'" );
        
        try {
	            stmt = c.createStatement();
	            
            try (ResultSet rs = stmt.executeQuery( "SELECT * FROM User WHERE USERNAME= " + "'" + textName.getText() + "'" 
                    + " AND Password= " + "'" + passwordField.getText() + "'")) {
                while ( rs.next() ) {
                    if (rs.getString("Username") != null && rs.getString("Password") != null) {
                        String  username = rs.getString("Username");
                        System.out.println( "USERNAME = " + username );
                        String password = rs.getString("Password");
                        System.out.println("PASSWORD = " + password);
                        persona = new User(rs.getString("ID"),rs.getString("Username"),rs.getString("Password"),rs.getString("Permessi"));
                        let_in = true;
                    }
                }
                    rs.close();
	            stmt.close();
            }      
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }
            System.out.println("Operation done successfully");
            return let_in;
    }  

    
    
    
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        
        connection = new SQLliteConnection("applicazione.db");
        c = connection.getConnection();
        
      }
}

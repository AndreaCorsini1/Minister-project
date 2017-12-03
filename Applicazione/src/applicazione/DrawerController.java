package applicazione;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DrawerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button homeBtn;

    @FXML
    private Button logout;

    @FXML
    void logout(ActionEvent event) {
        
        try {
						
				        TestController.connection.getConnection().close(); //chiude la connessione al database
						Parent login = FXMLLoader.load(getClass().getResource("Test.fxml"));
				        Scene login_scene = new Scene(login);
				        Stage app_stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				        app_stage.hide();
				        app_stage.setScene(login_scene);
				        app_stage.show();

			        
					} catch (IOException | SQLException e) {
					e.printStackTrace();
					}

    }

    @FXML
    void initialize() {
        assert homeBtn != null : "fx:id=\"homeBtn\" was not injected: check your FXML file 'DrawerView.fxml'.";
        assert logout != null : "fx:id=\"logout\" was not injected: check your FXML file 'DrawerView.fxml'.";

    }
}

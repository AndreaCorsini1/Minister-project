package applicazione;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainAppController implements Initializable{

    
    VBox sidePane;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane anchorPane;

    
    //Rimpiazza l'AnchorPane figlio, con la nuova schermata selezionata
    public void setNode(Node node) {

        anchorPane.getChildren().clear();
        anchorPane.getChildren().add((Node) node);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //carico il drawer e in base al bottone schiacciato con il for rimpiazzo l'AnchorPane figlio
        try {
            sidePane = FXMLLoader.load(getClass().getResource("DrawerView.fxml"));
            AnchorPane homeView = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
            drawer.setSidePane(sidePane);
            drawer.open();
            setNode(homeView);
            for (Node node : sidePane.getChildren()) {
                if (node.getAccessibleText() != null) {
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    	
                        switch (node.getAccessibleText()) {
                        
                        	case ("homeBtn"):
                        		drawer.open();
								setNode(homeView);                         
                            	break;
                            
                            case ("usersBtn"):
                                drawer.open();
								try {
									AnchorPane superUtente = FXMLLoader.load(getClass().getResource("UserView.fxml"));
									setNode(superUtente);
								} catch (IOException e1) {
										e1.printStackTrace();
									}                         
                                break;
                                	
                            case ("statisticsBtn"):
                                drawer.open();
								try {
									AnchorPane statistics = FXMLLoader.load(getClass().getResource("StatisticsView.fxml"));
									setNode(statistics);
								} catch (IOException e1) {
										e1.printStackTrace();
									}                         
                                break;
                                
                             case ("anagraficaBtn"):
                            	drawer.open();
                             	try{
                                   AnchorPane orderTableView = FXMLLoader.load(getClass().getResource("DatabaseView.fxml"));
                                   setNode(orderTableView);
                             	}catch(IOException e1){
                             		e1.printStackTrace();
                             	}
                         		break;
                                        
                            
                                
                        	}//fine switch
                    });
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Imposta l'animazione per il tasto del menu
        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED,(e)->{
            transition.setRate(transition.getRate()*-1);
            transition.play();
            
            if(drawer.isShown())
            {
                TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),hamburger);
                translateTransition.setFromX(0);
                translateTransition.setToX(-205);
                translateTransition.play();
                drawer.close();
                
                TranslateTransition paneTransition = new TranslateTransition(Duration.seconds(0.5),anchorPane);
                paneTransition.setFromX(0);
                paneTransition.setToX(-150);
                paneTransition.play();
            }else{
                TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),hamburger);
                translateTransition.setFromX(-205);
                translateTransition.setToX(0);
                translateTransition.play();
                drawer.open();

                TranslateTransition paneTransition = new TranslateTransition(Duration.seconds(0.5),anchorPane);
                paneTransition.setFromX(-150);
                paneTransition.setToX(0);
                paneTransition.play();
            }
        });
    }
    }


package mvc.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mvc.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private TextField username;

    @FXML
    void userLogIn(ActionEvent event) {
        LOGGER.info(username.getText() + " LOGGED IN" );

            // load the person detail with an empty person
        // call the main controller switch view method
        MainController.getInstance().switchView(ScreenType.PERSONLIST);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


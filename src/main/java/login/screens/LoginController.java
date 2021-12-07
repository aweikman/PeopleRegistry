package login.screens;

import javafx.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import login.gateway.AuthenticationGateway;
import login.gateway.Session;
import mvc.screens.MyController;
import mvc.screens.ScreenType;
import myexceptions.UnauthorizedException;
import myexceptions.UnknownException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable, MyController {

    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private PasswordField password;

    @FXML
    private TextField login;

    @FXML
    void doLogin(ActionEvent event) {

        String login = this.login.getText();
        String pass = this.password.getText();
        LOGGER.info(login + " " + pass);

        // check login with logingateway
        // login will throw an exception if it fails
        Session session = null;
        try {
            session = AuthenticationGateway.login(login, pass);
        } catch(UnauthorizedException e) {
            Alerts.infoAlert("Login failed!", "Either your username or your password is incorrect.");
            return;
        } catch(UnknownException e1) {
            Alerts.infoAlert("Login failed!", "Something bad happened: " + e1.getMessage());
            return;
        }
        // if ok. transition to person list
        MainController.getInstance().setSession(session);
        String lastName = "";
        MainController.getInstance().switchView(ScreenType.PERSONLIST, 0, lastName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}



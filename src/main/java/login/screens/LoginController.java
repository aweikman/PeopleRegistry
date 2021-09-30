package login.screens;

import javafx.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import myexceptions.UnauthorizedException;
import myexceptions.UnknownException;
import login.gateway.LoginGateway;
import login.gateway.Session;
import mvc.screens.MyController;
import mvc.screens.ScreenType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pw_hash.HashUtils;

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
        String hashedPassword = HashUtils.getCryptoHash(this.password.getText(), "SHA-256");
        LOGGER.info(this.password.getText() + " hashes to " + hashedPassword);
        LOGGER.info(login + " " + hashedPassword);

        // check login with logingateway
        // login will throw an exception if it fails
        Session session = null;
        try {
            session = LoginGateway.login(login, hashedPassword);
        } catch(UnauthorizedException e) {
            Alerts.infoAlert("Login failed!", "Either your username or your password is incorrect.");
            return;
        } catch(UnknownException e1) {
            Alerts.infoAlert("Login failed!", "Something bad happened: " + e1.getMessage());
            return;
        }
        // if ok. transition to person list
        MainController.getInstance().setSession(session);
        MainController.getInstance().switchView(ScreenType.PERSONLIST);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}



package mvc.screens;

import gateway.PersonGateway;
import javafx.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import login.gateway.PersonGatewayAPI;
import login.gateway.Session;
import login.screens.MainController;
import mvc.model.Person;
import myexceptions.UnauthorizedException;
import myexceptions.UnknownException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PersonDetailController implements Initializable, MyController {

    private static final Logger LOGGER = LogManager.getLogger();

    private Person person;

    private PersonGateway personGateway;

    public PersonDetailController(Person person) {
        this.person = person;
    }

    private Session session;

    @FXML
    private TextField personFirstName;

    @FXML
    private TextField personLastName;

    @FXML
    private TextField personDateOfBirth;

    @FXML
    private TextField personAge;

    @FXML
    private TextField personId;

    @FXML
    void cancel(ActionEvent event) {
        LOGGER.info("CANCEL");
        MainController.getInstance().switchView(ScreenType.PERSONLIST);
    }

    @FXML
    void save(ActionEvent event) {
        // temporary catch for empty fields to make sure user does not input empty lines
        if(personId.getText().isEmpty() || personFirstName.getText().isEmpty() || personLastName.getText().isEmpty() || personDateOfBirth.getText().isEmpty() || personAge.getText().isEmpty())
        {
            Alerts.infoAlert("Error", "Please fill in all fields");
        }

        else if(person.getId() == 0) {
            LOGGER.info("CREATING");
            // TODO: validate text fields FIRST before you save them to model
            person.setId(Integer.parseInt(personId.getText()));
            person.setDateOfBirth(personDateOfBirth.getText());
            person.setPersonFirstName(personFirstName.getText());
            person.setPersonLastName(personLastName.getText());

            try {
                person.setAge(Integer.parseInt(personAge.getText()));
            } catch(NumberFormatException e) {
                // TODO: find and plug in your alert helper functions
                // Alert errorAlert = new Alert(Alert.AlertType.ERROR)
                LOGGER.error("Person age must be an integer within valid range");
                return;
            }

            PersonGatewayAPI.addPerson(person);

            System.out.print(person.getId());
            // transition to personlist
            MainController.getInstance().switchView(ScreenType.PERSONLIST);
        }

        else
        {
            LOGGER.info("UPDATING");
            // TODO: validate text fields FIRST before you save them to model
            person.setId(Integer.parseInt(personId.getText()));
            person.setDateOfBirth(personDateOfBirth.getText());
            person.setPersonFirstName(personFirstName.getText());
            person.setPersonLastName(personLastName.getText());

            try {
                person.setAge(Integer.parseInt(personAge.getText()));
            } catch(NumberFormatException e) {
                // TODO: find and plug in your alert helper functions
                // Alert errorAlert = new Alert(Alert.AlertType.ERROR)
                LOGGER.error("Hey man, person age must be an integer from 0 to 40");
                return;
            }


            // transition to personlist
            MainController.getInstance().switchView(ScreenType.PERSONLIST);
        }


        // TODO: save the data to the database somewhere
        // TODO: what id db save fails?
        // ALERT


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // this is where we connect the model data to the GUI components like textfields
        personId.setText("" +person.getId());
        personFirstName.setText(person.getPersonFirstName());
        personLastName.setText(person.getPersonLastName());
        personDateOfBirth.setText(String.valueOf(person.getDateOfBirth()));
        personAge.setText("" + person.getAge());
    }


}

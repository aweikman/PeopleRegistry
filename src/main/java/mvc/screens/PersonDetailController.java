package mvc.screens;

import javafx.Alerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import login.gateway.PersonGatewayAPI;
import login.gateway.Session;
import login.screens.MainController;
import mvc.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonDetailController implements Initializable, MyController {
    private static final Logger LOGGER = LogManager.getLogger();

    private PersonGatewayAPI personGateway;

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

    private Person person;

    public PersonDetailController(Person person) {
        this.person = person;
    }

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
//            personGateway.addPerson(session.getSessionId());
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

package mvc.screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import mvc.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PersonDetailController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private TextField personFirstName;

    @FXML
    private TextField personLastName;

    @FXML
    private TextField personDateofBirth;

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
        if(person.getId() == 0) {
            LOGGER.info("CREATING");
        }
        else
        {
            LOGGER.info("UPDATING");
        }

        // TODO: validate text fields FIRST before you save them to model
        person.setId(Integer.parseInt(personId.getText()));
        person.setDateOfBirth(LocalDate.parse(personDateofBirth.getText()));
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

        // TODO: save the data to the database somewhere
        // TODO: what id db save fails?
        // ALERT

        // transition to personlist
        MainController.getInstance().switchView(ScreenType.PERSONLIST);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // this is where we connect the model data to the GUI components like textfields
        personId.setText("" +person.getId());
        personFirstName.setText(person.getPersonFirstName());
        personLastName.setText(person.getPersonLastName());
        personDateofBirth.setText(String.valueOf(person.getDateOfBirth()));
        personAge.setText("" + person.getAge());
    }


}

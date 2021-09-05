package mvc.screens;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import mvc.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonDetailController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private TextField personName;

    @FXML
    private TextField personAge;

    private Person person;

    public PersonDetailController(Person person) {
        this.person = person;
    }

    @FXML
    void cancelSave(ActionEvent event) {
        LOGGER.info("Cancel clicked");
    }

    @FXML
    void save(ActionEvent event) {
        LOGGER.info("Save clicked");

        // TODO: validate text fields FIRST before you save them to model
        person.setPersonName(personName.getText());
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
        personName.setText(person.getPersonName());
        personAge.setText("" + person.getAge());
    }


}

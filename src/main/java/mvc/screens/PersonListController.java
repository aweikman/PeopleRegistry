package mvc.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import login.gateway.PersonGatewayAPI;
import login.screens.MainController;
import mvc.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PersonListController implements Initializable, MyController {
    private static final Logger LOGGER = LogManager.getLogger();

    private ArrayList<Person> people;
    @FXML
    private ListView<Person> personList;

    public PersonListController(ArrayList<Person> people) {
        this.people = people;
    }

    @FXML
    void clickPerson(MouseEvent click) {
        // checks for double click
        if (click.getClickCount() == 2 && personList.getSelectionModel().getSelectedItem() != null) {

            // loads selected item from personList
            MainController.getInstance().switchView(ScreenType.PERSONDETAIL, personList.getSelectionModel().getSelectedItem());

            // logs READING when existing person is selected
            LOGGER.info("READING");
        }
    }
    @FXML
    void addPerson(ActionEvent event) {
        LOGGER.info("ADDING");

        // load the person detail with an empty person
        // call the main controller switch view method
        MainController.getInstance().switchView(ScreenType.PERSONDETAIL, new Person("", "", "01-01-2021", 0));
    }

    @FXML
    void deletePerson(ActionEvent event) {

        if (personList.getSelectionModel().getSelectedItem() == null)
        {
            LOGGER.info("ITEM NOT SELECTED");
            return;
        }

//        if (personList.getSelectionModel().getSelectedItem().getId() == 1) {
        LOGGER.info("DELETING " + personList.getSelectionModel().getSelectedItem().getPersonFirstName());
        PersonGatewayAPI.deletePerson(personList.getSelectionModel().getSelectedItem());
        MainController.getInstance().switchView(ScreenType.PERSONLIST);

//        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. turn plain ol arraylist of models into an ObservableArrayList
        ObservableList<Person> tempList = FXCollections.observableArrayList(people);

        // 2. plug the observable array list into the list
        personList.setItems(tempList);
    }
}


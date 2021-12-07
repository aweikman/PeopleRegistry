package mvc.screens;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import login.gateway.PersonGatewayAPI;
import login.screens.MainController;
import mvc.model.FetchResults;
import mvc.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PersonListController implements Initializable, MyController {

    private static final Logger LOGGER = LogManager.getLogger();

    private ArrayList<Person> people;

    private FetchResults fetchResults;

    String lastName = "";

    @FXML
    private ListView<Person> personList;

//    public PersonListController(ArrayList<Person> people) {
//        this.people = people;
//    }

    @FXML
    private TextField searchBar;

    @FXML
    private TextField resultField;

    public PersonListController(FetchResults fetchResults, String lastName) {
        this.fetchResults = fetchResults;
        this.lastName = lastName;
    }

    @FXML
    void search(){
        lastName = searchBar.getText();
//        System.out.println("Last name test " + lastName);
        MainController.getInstance().switchView(ScreenType.PERSONLIST, 0, lastName);
    }

    @FXML
    void nextPage(){
        int offset = fetchResults.getOffSet();
        if(offset >= fetchResults.getTotalElements()/10-1){
            offset = fetchResults.getTotalPages()-1;
        }
        else{
            offset += 1;
        }
        MainController.getInstance().switchView(ScreenType.PERSONLIST, offset, lastName);
    }

    @FXML
    void previousPage(){
        int offset = fetchResults.getOffSet();
        if(offset < 1){
            offset = 0;
        }
        else{
            offset -= 1;
        }
        MainController.getInstance().switchView(ScreenType.PERSONLIST, offset, lastName);
    }

    @FXML
    void firstPage(){
        MainController.getInstance().switchView(ScreenType.PERSONLIST, 0, lastName);
    }

    @FXML
    void lastPage(){
        MainController.getInstance().switchView(ScreenType.PERSONLIST, fetchResults.getTotalPages()-1, lastName);
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
        MainController.getInstance().switchView(ScreenType.PERSONDETAIL, new Person());
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

        resultField.setText("Fetching records " + (fetchResults.getFirstIndex()+1) + " to " + fetchResults.getLastIndex() + " out of " + fetchResults.getTotalElements());

        // 1. turn plain ol arraylist of models into an ObservableArrayList
        ObservableList<Person> tempList = FXCollections.observableArrayList(fetchResults.getPeople());

        // 2. plug the observable array list into the list
        personList.setItems(tempList);
    }
}


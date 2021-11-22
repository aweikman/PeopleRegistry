package mvc.screens;

import gateway.PersonGateway;
import javafx.Alerts;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import login.gateway.PersonGatewayAPI;
import login.gateway.Session;
import login.screens.MainController;
import mvc.model.AuditTrail;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PersonDetailController implements Initializable, MyController {

    private static final Logger LOGGER = LogManager.getLogger();

    private Person person;

    private ObservableList<AuditTrail> auditTrail;

    private PersonGateway personGateway;

    public PersonDetailController(Person person, ArrayList<AuditTrail> auditTrail) {
        this.person = person;
        this.auditTrail = FXCollections.observableArrayList(auditTrail);
    }

    public PersonDetailController(Person person) {
        this.person = person;
    }

    private Session session;

    //detail fxmls
    @FXML
    private TextField personFirstName;

    @FXML
    private TextField personLastName;

    @FXML
    private TextField personDateOfBirth;

    @FXML
    private TextField personId;

    //audit trail fxmls
    @FXML
    private TableColumn personChanged;

    @FXML
    private TableColumn changeMsg;

    @FXML
    private TableColumn whenOccured;

    @FXML
    private TableColumn changedBy;

    @FXML
    private TableView table;



    @FXML
    void cancel(ActionEvent event) {
        LOGGER.info("CANCEL");
        MainController.getInstance().switchView(ScreenType.PERSONLIST);
    }

    @FXML
    void save(ActionEvent event) {

        // code for checking if entered date is after current date
        Date date = null;
        SimpleDateFormat sdf = null;
        try {
            // set format for entered date
            sdf = new SimpleDateFormat("MM-dd-yyyy");

            // instantiate date entered
            date = sdf.parse(personDateOfBirth.getText());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // instantiate current date to compare entered date
        Date currentDate = new Date();

        // temporary catch for empty fields to make sure user does not input empty lines
        if (personId.getText().isEmpty() || personFirstName.getText().isEmpty() || personLastName.getText().isEmpty() || personDateOfBirth.getText().isEmpty()) {
            Alerts.infoAlert("Error", "Please fill in all fields");
        }
        if (date.after(currentDate)) {
            Alerts.infoAlert("Error", "Date of birth must be before current date");
        }

        boolean checkFormat;
        if (personDateOfBirth.getText().matches("([0-9]{2})-([0-9]{2})-([0-9]{4})"))
            checkFormat=true;
        else
            checkFormat=false;
        if (checkFormat == false)
        {
            Alerts.infoAlert("Error", "Date of birth must be entered as MM-dd-yyyy");
        }
        else if (person.getId() == 0) {
            LOGGER.info("CREATING");
            // TODO: validate text fields FIRST before you save them to model
            person.setDateOfBirth(personDateOfBirth.getText());
            person.setPersonFirstName(personFirstName.getText());
            person.setPersonLastName(personLastName.getText());

            PersonGatewayAPI.addPerson(person);
            // transition to personlist
            MainController.getInstance().switchView(ScreenType.PERSONLIST);
        }
        else {
            LOGGER.info("UPDATING");
            person.setDateOfBirth(personDateOfBirth.getText());
            person.setPersonFirstName(personFirstName.getText());
            person.setPersonLastName(personLastName.getText());

            PersonGatewayAPI.updatePerson(person);
            // transition to personlist
            MainController.getInstance().switchView(ScreenType.PERSONLIST);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // this is where we connect the model data to the GUI components like textfields
        personId.setText("" +person.getId());
        personFirstName.setText(person.getPersonFirstName());
        personLastName.setText(person.getPersonLastName());
        personDateOfBirth.setText(String.valueOf(person.getDateOfBirth()));

        // this is where we connect the model data of AugditTrail to gui tableview components
        if(person.getId() != 0 ) {
            changeMsg.setCellValueFactory(new PropertyValueFactory<AuditTrail, String>("changeMsg"));
            changedBy.setCellValueFactory(new PropertyValueFactory<AuditTrail, Integer>("changedBy"));
            personChanged.setCellValueFactory(new PropertyValueFactory<AuditTrail, Integer>("personId"));
            whenOccured.setCellValueFactory(new PropertyValueFactory<AuditTrail, Instant>("whenOccured"));
            table.setItems(auditTrail);
        }
    }


}

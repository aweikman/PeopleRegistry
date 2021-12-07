package mvc.screens;

import com.mysql.cj.jdbc.MysqlDataSource;
import gateway.PersonGateway;
import javafx.Alerts;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

public class PersonDetailController implements Initializable, MyController {

    private static final Logger LOGGER = LogManager.getLogger();

    private Person person;

    private ObservableList<AuditTrail> auditTrail;

    private PersonGateway personGateway;

    private static final Random roller = new Random();

    private static Connection conn = null;

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
        MainController.getInstance().switchView(ScreenType.PERSONLIST, 0, "");
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
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                DataSource ds = getDataSource();
                conn = ds.getConnection();

                System.out.println("grabbing timestamp...");
                stmt = conn.prepareStatement("select last_modified from people where id = 1");
                rs = stmt.executeQuery();
                rs.next();
                Timestamp ts1 = rs.getTimestamp("last_modified");

                System.out.println("checking timestamps...");

                Timestamp ts2 = Timestamp.from(person.getLastModified());

                //System.out.println("Timestamp 1 " + ts1 + "Timestamp 2 " + ts2);

                if(!ts2.equals(ts1)) {
                    Alerts.infoAlert("Update Error", "Another user has just made a change to this record");
                    return;
                } else {
                    LOGGER.info("UPDATING");
                    person.setDateOfBirth(personDateOfBirth.getText());
                    person.setPersonFirstName(personFirstName.getText());
                    person.setPersonLastName(personLastName.getText());

                    PersonGatewayAPI.updatePerson(person);
                    // transition to personlist
                    MainController.getInstance().switchView(ScreenType.PERSONLIST);
                }

                System.out.println("done.");

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    if(rs != null)
                        rs.close();
                    if(stmt != null)
                        stmt.close();
                    conn.close();
                } catch(Exception e) {

                }
            }
        }
    }

    public static DataSource getDataSource() {
        try {
            MysqlDataSource mysqlDS = new MysqlDataSource();
            mysqlDS.setURL("jdbc:mysql://cs3743.fulgentcorp.com:3306/cs4743_vrn320?serverTimezone=UTC#");
            mysqlDS.setUser("vrn320");
            mysqlDS.setPassword("KLkdnbefN2ATdpqyMtVo");
            return mysqlDS;
        } catch(RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // this is where we connect the model data to the GUI components like textfields
        personId.setText("" +person.getId());
        personFirstName.setText(person.getPersonFirstName());
        personLastName.setText(person.getPersonLastName());
        personDateOfBirth.setText("01-01-2000");

        // this is where we connect the model data of AuditTrail to gui tableview components
        if(person.getId() != 0 ) {
            changeMsg.setCellValueFactory(new PropertyValueFactory<AuditTrail, String>("changeMsg"));
            changedBy.setCellValueFactory(new PropertyValueFactory<AuditTrail, Integer>("changedBy"));
            personChanged.setCellValueFactory(new PropertyValueFactory<AuditTrail, Integer>("personId"));
            whenOccured.setCellValueFactory(new PropertyValueFactory<AuditTrail, Instant>("whenOccured"));
            table.setItems(auditTrail);
        }
    }




}

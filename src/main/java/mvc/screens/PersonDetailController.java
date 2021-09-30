////package mvc.screens;
////
////import javafx.event.ActionEvent;
////import javafx.fxml.FXML;
////import javafx.fxml.Initializable;
////import javafx.scene.control.Alert;
////import javafx.scene.control.TextField;
////import login.screens.MainController;
////import mvc.model.Person;
////import org.apache.logging.log4j.LogManager;
////import org.apache.logging.log4j.Logger;
////
////import java.net.URL;
////import java.time.LocalDate;
////import java.util.ResourceBundle;
////
////public class PersonDetailController implements Initializable {
////    private static final Logger LOGGER = LogManager.getLogger();
////
////    @FXML
////    private TextField personFirstName;
////
////    @FXML
////    private TextField personLastName;
////
////    @FXML
////    private TextField personDateofBirth;
////
////    @FXML
////    private TextField personAge;
////
////    @FXML
////    private TextField personId;
////
////    private Person person;
////
////    public PersonDetailController(Person person) {
////        this.person = person;
////    }
////
////    @FXML
////    void cancel(ActionEvent event) {
////        LOGGER.info("CANCEL");
////        MainController.getInstance().switchView(ScreenType.PERSONLIST);
////    }
////
////    @FXML
////    void save(ActionEvent event) {
////        // temporary catch for empty fields to make sure user does not input empty lines
////        if(personId.getText().isEmpty() || personFirstName.getText().isEmpty() || personLastName.getText().isEmpty() || personDateofBirth.getText().isEmpty() || personAge.getText().isEmpty())
////        {
////            Alert fail= new Alert(Alert.AlertType.INFORMATION);
////            fail.setHeaderText("ERROR");
////            fail.setContentText("Please fill in all the fields");
////            fail.showAndWait();
////        }
////        else if(person.getId() == 0) {
////            LOGGER.info("CREATING");
////            // TODO: validate text fields FIRST before you save them to model
////            person.setId(Integer.parseInt(personId.getText()));
////            person.setDateOfBirth(LocalDate.parse(personDateofBirth.getText()));
////            person.setPersonFirstName(personFirstName.getText());
////            person.setPersonLastName(personLastName.getText());
////            try {
////                person.setAge(Integer.parseInt(personAge.getText()));
////            } catch(NumberFormatException e) {
////                // TODO: find and plug in your alert helper functions
////                // Alert errorAlert = new Alert(Alert.AlertType.ERROR)
////                LOGGER.error("Hey man, person age must be an integer from 0 to 40");
////                return;
////            }
////            // transition to personlist
////            MainController.getInstance().switchView(ScreenType.PERSONLIST);
////        }
////        else
////        {
////            LOGGER.info("UPDATING");
////            // TODO: validate text fields FIRST before you save them to model
////            person.setId(Integer.parseInt(personId.getText()));
////            person.setDateOfBirth(LocalDate.parse(personDateofBirth.getText()));
////            person.setPersonFirstName(personFirstName.getText());
////            person.setPersonLastName(personLastName.getText());
////            try {
////                person.setAge(Integer.parseInt(personAge.getText()));
////            } catch(NumberFormatException e) {
////                // TODO: find and plug in your alert helper functions
////                // Alert errorAlert = new Alert(Alert.AlertType.ERROR)
////                LOGGER.error("Hey man, person age must be an integer from 0 to 40");
////                return;
////            }
////            // transition to personlist
////            MainController.getInstance().switchView(ScreenType.PERSONLIST);
////        }
////
////
////
////
////
////        // TODO: save the data to the database somewhere
////        // TODO: what id db save fails?
////        // ALERT
////
////
////    }
////
////    @Override
////    public void initialize(URL url, ResourceBundle resourceBundle) {
////        // this is where we connect the model data to the GUI components like textfields
////        personId.setText("" +person.getId());
////        personFirstName.setText(person.getPersonFirstName());
////        personLastName.setText(person.getPersonLastName());
////        personDateofBirth.setText(String.valueOf(person.getDateOfBirth()));
////        personAge.setText("" + person.getAge());
////    }
////
////
////}
//package mvc.screens;
//
//import javafx.Alerts;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.TextField;
//import mvc.model.Person;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class PersonDetailController implements Initializable, MyController {
//    private static final Logger LOGGER = LogManager.getLogger();
//
//    @FXML
//    private TextField personName;
//
//    @FXML
//    private TextField personAge;
//
//    private Person person;
//
//    public PersonDetailController(Person person) {
//        this.person = person;
//    }
//
//    @FXML
//    void cancelSave(ActionEvent event) {
//        LOGGER.info("Cancel clicked");
//    }
//
//    @FXML
//    void save(ActionEvent event) {
//        LOGGER.info("Save clicked");
//
//        Alerts.infoAlert("A title", "A message");
//
//        // TODO: validate text fields FIRST before you save them to model
//        person.setPersonName(personName.getText());
//        try {
//            person.setAge(Integer.parseInt(personAge.getText()));
//        } catch(NumberFormatException e) {
//            // TODO: find and plug in your alert helper functions
//            // Alert errorAlert = new Alert(Alert.AlertType.ERROR)
//            LOGGER.error("Person age must be an integer with in range");
//            return;
//        }
//
//        // TODO: save the data to the database somewhere
//        // TODO: what id db save fails?
//        // ALERT
//
//        // transition to personlist
//        MainController.getInstance().switchView(ScreenType.PERSONLIST);
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        // this is where we connect the model data to the GUI components like textfields
//        personName.setText(person.getPersonName());
//        personAge.setText("" + person.getAge());
//    }
//
//
//}

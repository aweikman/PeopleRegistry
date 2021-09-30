//package wsclient;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import model.Dog;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class LoginController implements Initializable {
//    @FXML
//    private TextField userName;
//
//    @FXML
//
//    private PasswordField password;
//
//    @FXML
//    void doLogin(ActionEvent event) {
//        try {
//            // authenticate using given credentials
//            String sessionId = SessionGateway.authenticate(userName.getText(), password.getText());
//            // dont need to check validity of sessionId as all other web service calls will check that
//
//            // fetch the dog list
//            DogGateway gw = new DogGateway("http://localhost:8080/dogs",  sessionId);
//            List<Dog> dogs = gw.getDogs();
//            System.out.println("dogs are " + dogs);
//
//            // switch to the dog view
//            // we will create a new stage but don't do this in your assignment
//            Stage newStage = new Stage();
//            DogListController controller = new DogListController(dogs);
//            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/widgetlistview.fxml"));
//            loader.setController(controller);
//            Parent rootNode = loader.load();
//            Scene scene = new Scene(rootNode);
//            newStage.setScene(scene);
//            newStage.show();
//
//        } catch(RuntimeException | IOException e) {
//            //TODO: show nice GUI popup saying I dont know you
//            System.out.println("Authentication failed!");
//            e.printStackTrace();
//        }
//
//    }
//
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        userName.setText("ragnar");
//        password.setText("flapjacks");
//    }
//}

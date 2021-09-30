package wsclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DogFXDemo extends Application {

    public static void main(String [] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(DogFXDemo.class.getResource("/login.fxml"));
        LoginController controller = new LoginController();
        loader.setController(controller);
        Parent rootNode = loader.load();
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.setTitle("Dog FX Demo");
        stage.show();
    }
}

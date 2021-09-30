package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.screens.MainController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppMain extends Application {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        LOGGER.info("before launch");
        launch(args);
        LOGGER.info("after launch");
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Override
    public void start(Stage stage) throws Exception {
        LOGGER.info("before start");

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/views/mvc/main_view.fxml"));
        loader.setController(MainController.getInstance());
        Parent rootNode = loader.load();
        stage.setScene(new Scene(rootNode));

        stage.setTitle("Project");
        stage.show();

        LOGGER.info("after start");
    }
}
package mvc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mvc.model.Person;
import mvc.screens.MainController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;

public class  AppMain extends Application {
    private static final Logger LOGGER = LogManager.getLogger();

    private ArrayList<Person> people;


    public static void main(String[] args) {
        LOGGER.info("before launch");
        launch(args);
        LOGGER.info("after launch");
    }

    @Override
    public void init() throws Exception {
        super.init();
        LOGGER.info("in init");
        people = new ArrayList<>();
        people.add(new Person(10, "Bob", "dole", LocalDate.of(1993, 2, 19), 27));
        people.add(new Person(12, "Tom", "jerry", LocalDate.of(1993, 2, 19),27));
        people.add(new Person(11, "Old","yeller", LocalDate.of(1993, 2, 19),27));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        LOGGER.info("in stop");
    }

    @Override
    public void start(Stage stage) throws Exception {
        LOGGER.info("before start");

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/main_view.fxml"));
        loader.setController(MainController.getInstance());
        MainController.getInstance().setPerson(people);
        Parent rootNode = loader.load();
        stage.setScene(new Scene(rootNode));

        stage.setTitle("Assignment 1");
        stage.show();

        LOGGER.info("after start");
    }
}

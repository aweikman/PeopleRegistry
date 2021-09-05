package mvc.screens;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import mvc.model.Person;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static MainController instance = null;

    private ArrayList<Person> people;

    @FXML
    private BorderPane rootPane;

    private MainController() {
    }

    public void switchView(ScreenType screenType, Object... args) {
        switch (screenType) {
            case PERSONLIST:
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/person_list.fxml"));
                loader.setController(new PersonListController(people));
                Parent rootNode = null;
                try {
                    rootNode = loader.load();
                    rootPane.setCenter(rootNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case PERSONDETAIL:
                loader = new FXMLLoader(this.getClass().getResource("/person_detail.fxml"));
                // TODO: need to give the controller a person object that is passed into switchView
                if(!(args[0] instanceof Person)) {
                    throw new IllegalArgumentException("Hey that's not a person! " + args[0].toString());
                }
                loader.setController(new PersonDetailController((Person) args[0]));
                rootNode = null;
                try {
                    rootNode = loader.load();
                    rootPane.setCenter(rootNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchView(ScreenType.PERSONLIST);
    }

    public static MainController getInstance() {
        if(instance == null)
            instance = new MainController();
        return instance;
    }

    // accessors

    public ArrayList<Person> getPerson() {
        return people;
    }

    public void setPerson(ArrayList<Person> people) {
        this.people = people;
    }
}

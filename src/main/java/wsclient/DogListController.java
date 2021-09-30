package wsclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Dog;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DogListController implements Initializable {
    private ObservableList dogList;

    @FXML
    private ListView<Dog> widgetListView;

    public DogListController(List<Dog> dogs) {
        dogList = FXCollections.observableArrayList(dogs);
    }

    @FXML
    void doSomething(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        widgetListView.setItems(dogList);
    }
}

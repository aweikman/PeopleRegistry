package login.screens;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import login.gateway.PersonGateway;
import login.gateway.Session;
import mvc.model.Person;
import mvc.screens.PersonDetailController;
import mvc.screens.PersonListController;
import mvc.screens.MyController;
import mvc.screens.ScreenType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger();

    private static MainController instance = null;

    private Session session;

    private PersonGateway personGateway;

    @FXML
    private BorderPane rootPane;

    private MainController() {
        personGateway = new PersonGateway();
    }

    public void switchView(ScreenType screenType, Object... args) {
        String viewFileName = "";
        MyController controller = null;

        LOGGER.info(session);

        switch (screenType) {
            case PERSONLIST:
                viewFileName = "/views/mvc/person_list.fxml";
                ArrayList<Person> people = (ArrayList<Person>) personGateway.fetchPeople(session.getSessionId());
                controller = new PersonListController(people);
                break;
            case PERSONDETAIL:
                viewFileName = "/views/mvc/person_detail.fxml";
                if(!(args[0] instanceof Person)) {
                    throw new IllegalArgumentException("Hey that's not a person! " + args[0].toString());
                }
                controller = new PersonDetailController((Person) args[0]);
                break;
            case LOGIN:
                viewFileName = "/views/mvc/login.fxml";
                controller = new LoginController();
                break;
            default:
                LOGGER.error("unknown screen type in switchView: " + screenType);
                return;
        }
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(viewFileName));
        loader.setController(controller);
        Parent rootNode = null;
        try {
            rootNode = loader.load();
            rootPane.setCenter(rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switchView(ScreenType.LOGIN);
    }

    public static MainController getInstance() {
        if(instance == null)
            instance = new MainController();
        return instance;
    }

    // accessors

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public PersonGateway getPersonGateway() {
        return personGateway;
    }

    public void setPersonGateway(PersonGateway personGateway) {
        this.personGateway = personGateway;
    }
}


package login.screens;

import gateway.PersonGateway;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import login.gateway.PersonGatewayAPI;
import login.gateway.Session;
import mvc.model.AuditTrail;
import mvc.model.FetchResults;
import mvc.model.Person;
import mvc.screens.MyController;
import mvc.screens.PersonDetailController;
import mvc.screens.PersonListController;
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

    private AuditTrail auditTrail;

    private FetchResults fetchResults;

    @FXML
    private BorderPane rootPane;

    private MainController() {

    }

    public void switchView(ScreenType screenType, Object... args) {
        String viewFileName = "";
        MyController controller = null;

        LOGGER.info(session);

        switch(screenType) {
            case PERSONLIST:
                viewFileName = "/views/mvc/person_list.fxml";
                int page = 0;
                String lastName = "";
                if(args.length > 0)
                {
                    page = (int)args[0];
                }
                if(args.length > 1)
                {
                    lastName = (String)args[1];
                    if(lastName == null)
                    {
                        lastName = "";
                    }
                }

                ArrayList<Person> people = null;
                fetchResults = personGateway.fetchPeople(page, lastName);
                controller = new PersonListController(fetchResults, lastName);

                break;
            case PERSONDETAIL:
                viewFileName = "/views/mvc/person_detail.fxml";
                if(!(args[0] instanceof Person)) {
                    throw new IllegalArgumentException("Hey that's not a person! " + args[0].toString());
                }
                int id = ((Person)args[0]).getId();
                if(id == 0)
                {
                    controller = new PersonDetailController((Person)args[0]);
                    break;
                }
                ArrayList<AuditTrail> auditTrail = (ArrayList<AuditTrail>) personGateway.fetchAuditTrail(id);
                controller = new PersonDetailController((Person)args[0], auditTrail);
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

        if(personGateway instanceof PersonGatewayAPI)
            ((PersonGatewayAPI) personGateway).setToken(session.getSessionId());
    }

    public PersonGateway getPersonGateway() {
        return personGateway;
    }

    public void setPersonGateway(PersonGateway personGateway) {
        this.personGateway = personGateway;
    }

    public AuditTrail getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(AuditTrail auditTrail) {
        this.auditTrail = auditTrail;
    }

    public FetchResults getFetchResults() {
        return fetchResults;
    }

    public void setFetchResults(FetchResults fetchResults) {
        this.fetchResults = fetchResults;
    }

}


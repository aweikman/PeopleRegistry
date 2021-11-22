package gateway;

import javafx.scene.control.TableView;
import mvc.model.AuditTrail;
import mvc.model.Person;
import myexceptions.UnauthorizedException;
import myexceptions.UnknownException;

import java.util.List;

public interface PersonGateway {

    public abstract List<Person> fetchPeople() throws UnauthorizedException, UnknownException;

    public abstract List<AuditTrail> fetchAuditTrail(int id) throws UnauthorizedException, UnknownException;

}

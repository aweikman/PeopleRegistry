package gateway;

import mvc.model.Person;
import myexceptions.UnauthorizedException;
import myexceptions.UnknownException;

import java.util.List;

public interface PersonGateway {
    public abstract List<Person> fetchPeople() throws UnauthorizedException, UnknownException;
}
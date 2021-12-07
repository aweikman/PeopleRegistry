package gateway;

import mvc.model.AuditTrail;
import mvc.model.FetchResults;
import myexceptions.UnauthorizedException;
import myexceptions.UnknownException;

import java.util.List;

public interface PersonGateway {

    public abstract FetchResults fetchPeople(int page, String lastName) throws UnauthorizedException, UnknownException;

    public abstract List<AuditTrail> fetchAuditTrail(int id) throws UnauthorizedException, UnknownException;

}

package login.gateway;

public class SessionGateway {
    private String sessionId;
    private String userFullName;

//    public SessionGateway() {
//    }
//
//    public SessionGateway(String sessionId) {
//        this.sessionId = sessionId;
//        this.userFullName = "";
//    }

    public SessionGateway(String sessionId, String userFullName) {
        this.sessionId = sessionId;
        this.userFullName = userFullName;
    }

    @Override
    public String toString() {
        return "SessionGateway{" +
                "sessionId='" + sessionId + '\'' +
                ", userName='" + userFullName + '\'' +
                '}';
    }

    // accessors
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}

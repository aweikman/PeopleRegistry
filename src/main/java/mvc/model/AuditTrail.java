package mvc.model;

import org.json.JSONObject;

import java.time.Instant;

public class AuditTrail {
    private Integer id;
    private String changeMsg;
    private Integer changedBy;
    private Integer personId;
    private Instant whenOccured;

    public AuditTrail(String changeMsg, int userId, Integer id, String now) {
        this.changeMsg = changeMsg;
        this.changedBy = userId;
        this.personId = id;
        this.whenOccured = Instant.parse(now);
    }

    public static AuditTrail fromJSONObject(JSONObject json) {
        try {
            AuditTrail auditTrail = new AuditTrail(json.getString("changeMsg"),
                    json.getInt("changedBy"), json.getInt("personId"), json.getString("whenOccured"));
            return auditTrail;
        } catch(Exception e) {
            throw new IllegalArgumentException("Unable to parse person from provided json: " + json.toString());
        }
    }

    @Override
    public String toString() {
        return getId() + " " + getChangeMsg() + " " + getWhenOccured() + " " + getChangedBy();
    }

    // accessors
    public Instant getWhenOccured() {
        return whenOccured;
    }

    public void setWhenOccured(Instant whenOccured) {
        this.whenOccured = whenOccured;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Integer changedBy) {
        this.changedBy = changedBy;
    }

    public String getChangeMsg() {
        return changeMsg;
    }

    public void setChangeMsg(String changeMsg) {
        this.changeMsg = changeMsg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}


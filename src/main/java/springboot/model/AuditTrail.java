package springboot.model;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "audit_trail")
@Entity
public class AuditTrail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "change_msg", nullable = false, length = 1000)
    private String changeMsg;

    @Column(name = "changed_by", nullable = false)
    private Integer changedBy;

    @Column(name = "person_id", nullable = false)
    private Integer personId;

    @Column(name = "when_occured", nullable = false)
    private Instant whenOccured;

    public AuditTrail(String changeMsg, int userId, Integer id, Instant now) {
        this.changeMsg = changeMsg;
        this.changedBy = userId;
        this.personId = id;
        this.whenOccured = now;
    }

    public AuditTrail() {

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
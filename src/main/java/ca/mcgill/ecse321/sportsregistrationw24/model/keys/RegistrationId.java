package ca.mcgill.ecse321.sportsregistrationw24.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RegistrationId implements Serializable {
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "sport_session_id")
    private Integer sportSessionId;

    public RegistrationId() {}

    public RegistrationId(Integer customerId, Integer sportSessionId) {
        this.customerId = customerId;
        this.sportSessionId = sportSessionId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getSportSessionId() {
        return sportSessionId;
    }

    public void setSportSessionId(Integer sportSessionId) {
        this.sportSessionId = sportSessionId;
    }
}

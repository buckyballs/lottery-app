package com.oci.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by maqsoodi on 1/26/2017.
 */
@Entity
@Component
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer participantId = 1;

    @NotEmpty(message = "Please enter participant name.")
    private String name;

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Please enter valid email.")
    @Column(unique = true)
    private String email;

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

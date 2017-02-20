package com.oci.validators;

import com.oci.domain.Participant;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by maqsoodi on 2/20/2017.
 */
@Component
public class ParticipantValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Participant.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Participant participant = (Participant) target;

    }
}

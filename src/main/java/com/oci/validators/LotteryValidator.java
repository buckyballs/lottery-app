package com.oci.validators;

import com.oci.domain.Lottery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

/**
 * Created by Ishtiaq on 1/18/2017.
 */
@Component
public class LotteryValidator implements Validator {

    @Value("${lottery.password}")
    private String lotteryPassword;

    @Override
    public boolean supports(Class<?> clazz) {
        return Lottery.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Lottery lottery = (Lottery) target;

        if (!lottery.getPasswordText().equals(lotteryPassword)) {
            errors.rejectValue("passwordText", "PasswordsDontMatch.lotteryForm.passwordText", "Provided password is incorrect");
        }
        // separate null and valid date parts as we want to show date errors if any of this not valid, removed if else which was limiting error display scope
        if (lottery.getDrawingTime() == null) {
            errors.rejectValue("minutesToDraw", "NullDrawTimeDate.lotteryForm.drawingTime", "Please Enter Valid Minutes To Draw In Positive Number");
        }
        if (lottery.getDrawingTime() != null && lottery.getDrawingTime().before(new Date())) {
            errors.rejectValue("minutesToDraw", "PastDrawTimeDate.lotteryForm.drawingTime", "Please Enter Valid Minutes To Draw In Positive Number");
        }
    }
}
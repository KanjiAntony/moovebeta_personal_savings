package ke.co.kanji.moovebeta.personal_saving.utils;

import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaUser;
import ke.co.kanji.moovebeta.personal_saving.service.RegistrationService;
import ke.co.kanji.moovebeta.personal_saving.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MoovebetaUserValidationUtil implements Validator {

    @Autowired
    private RegistrationService registrationService;

    @Override
    public boolean supports(Class<?> clazz) {
        return MoovebetaUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MoovebetaUser moovebetaUser = (MoovebetaUser) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"email","RequiredField");

        if(registrationService.lookForEmail(moovebetaUser.getUsername()) != null) {
            errors.rejectValue("email","Registration.Email.Taken");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password","RequiredField");

        if(moovebetaUser.getPassword().length() <=8) {
            errors.rejectValue("password","Registration.Password.Length");
        }


    }
}

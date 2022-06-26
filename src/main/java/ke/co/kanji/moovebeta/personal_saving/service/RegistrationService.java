package ke.co.kanji.moovebeta.personal_saving.service;

import ke.co.kanji.moovebeta.personal_saving.dto.RegistrationRequest;
import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaUser;

public interface RegistrationService {
    void register(RegistrationRequest registrationRequest);
    MoovebetaUser lookForEmail(String email);
}

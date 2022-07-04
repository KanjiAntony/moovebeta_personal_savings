package ke.co.kanji.moovebeta.personal_saving.service;

import ke.co.kanji.moovebeta.personal_saving.dto.RegistrationRequest;
import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaUser;
import ke.co.kanji.moovebeta.personal_saving.repository.MoovebetaUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class RegistrationServiceImpl implements RegistrationService{

    private final PasswordEncoder passwordEncoder;

    private final MoovebetaUserRepository moovebetaUserRepository;

    public RegistrationServiceImpl(PasswordEncoder passwordEncoder, MoovebetaUserRepository moovebetaUserRepository) {
        this.passwordEncoder = passwordEncoder;
        this.moovebetaUserRepository = moovebetaUserRepository;
    }

    @Override
    public void register(RegistrationRequest registrationRequest) {

        MoovebetaUser user = new MoovebetaUser(
                registrationRequest.fullname,
                registrationRequest.email,
                registrationRequest.phoneNumber,
                passwordEncoder.encode(registrationRequest.password),
                registrationRequest.role,
                LocalDateTime.now()
        );

        moovebetaUserRepository.save(user);

        System.out.println("User registered");

    }

    @Override
    public MoovebetaUser lookForEmail(String email) {
        return moovebetaUserRepository.lookForEmail(email);
    }
}

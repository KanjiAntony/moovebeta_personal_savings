package ke.co.kanji.moovebeta.personal_saving.service;

import ke.co.kanji.moovebeta.personal_saving.dto.RegistrationRequest;
import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaUser;
import ke.co.kanji.moovebeta.personal_saving.repository.MoovebetaUserRepository;
import ke.co.kanji.moovebeta.personal_saving.security.roles_and_permissions.UserRoles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RegistrationServiceTest {

    @Mock private PasswordEncoder passwordEncoder;
    @Mock private MoovebetaUserRepository moovebetaUserRepository;
    private RegistrationService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new RegistrationServiceImpl(passwordEncoder,moovebetaUserRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canRegister() {

        //given
        // clock.withZone(ZoneId.of("UTC"));
        Clock c = Clock.fixed(Instant.parse("2014-12-22T10:15:30.00Z"), ZoneId.of("UTC"));

        RegistrationRequest registrationRequest = new RegistrationRequest(
                "Kanji",
                "kanjianto@gmail.com",
                0705,
                UserRoles.USER,
                "password"
        );

        MoovebetaUser userTest = new MoovebetaUser(
                registrationRequest.fullname,
                registrationRequest.email,
                registrationRequest.phoneNumber,
                passwordEncoder.encode(registrationRequest.getPassword()),
                UserRoles.USER,
                LocalDateTime.now(c)
        );

        //when
        underTest.register(registrationRequest);

        //then
        ArgumentCaptor<MoovebetaUser> moovebetaUserArgumentCaptor = ArgumentCaptor.forClass(MoovebetaUser.class);

        Mockito.verify(moovebetaUserRepository).save(moovebetaUserArgumentCaptor.capture());

        MoovebetaUser capturedMoovebetaUser = moovebetaUserArgumentCaptor.getValue();

        assertThat(capturedMoovebetaUser).isEqualTo(userTest);

    }

    @Test
    @Disabled
    void lookForEmail() {
    }
}
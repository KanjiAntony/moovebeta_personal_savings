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

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RegistrationServiceTest {

    @Mock private PasswordEncoder passwordEncoder;
    @Mock private MoovebetaUserRepository moovebetaUserRepository;
    private RegistrationService underTest;
    private AutoCloseable autoCloseable;

    @Mock private Clock clock;

    private static ZonedDateTime NOW = ZonedDateTime.of(
            2022,
            7,
            27,
            8,
            30,
            30,
            30,
            ZoneId.of("GMT")
    );

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        Mockito.when(clock.getZone()).thenReturn(NOW.getZone());
        Mockito.when(clock.instant()).thenReturn(NOW.toInstant());

        underTest = new RegistrationServiceImpl(passwordEncoder,moovebetaUserRepository, clock);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canRegister() {

        RegistrationRequest registrationRequest = new RegistrationRequest(
                "Kanji",
                "kanjianto@gmail.com",
                0705,
                UserRoles.USER,
                "password"
        );

        LocalDateTime registered_on = LocalDateTime.of(
                2022,
                7,
                27,
                8,
                30,
                39,
                30
        );

        MoovebetaUser userTest = new MoovebetaUser(
                registrationRequest.fullname,
                registrationRequest.email,
                registrationRequest.phoneNumber,
                passwordEncoder.encode(registrationRequest.password),
                UserRoles.USER,
                registered_on
        );

        //when
        underTest.register(registrationRequest);

        //then
        ArgumentCaptor<MoovebetaUser> moovebetaUserArgumentCaptor = ArgumentCaptor.forClass(MoovebetaUser.class);

        Mockito.verify(moovebetaUserRepository).save(moovebetaUserArgumentCaptor.capture());

        MoovebetaUser capturedMoovebetaUser = moovebetaUserArgumentCaptor.getValue();

        assertThat(capturedMoovebetaUser.getFullname()).isEqualTo(userTest.getFullname());

    }

    @Test
    @Disabled
    void lookForEmail() {
    }
}
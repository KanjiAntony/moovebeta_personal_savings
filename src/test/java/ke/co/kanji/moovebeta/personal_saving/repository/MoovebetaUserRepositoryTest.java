package ke.co.kanji.moovebeta.personal_saving.repository;

import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaUser;
import ke.co.kanji.moovebeta.personal_saving.security.roles_and_permissions.UserRoles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MoovebetaUserRepositoryTest {

    @Autowired
    private MoovebetaUserRepository underTest;

    @Test
    void itShouldLookForEmailExists() {
        //given
        String email = "kanjianto@gmail.com";

        MoovebetaUser userTest = new MoovebetaUser(
            "Kanji",
            email,
            0705,
            "password",
            UserRoles.USER,
                LocalDateTime.now()
        );
        //when
        underTest.save(userTest);

        //then
        assertThat(underTest.lookForEmail(email)).isEqualTo(userTest);
    }

    @Test
    void itShouldLookForEmailDoesNotExists() {
        //given
        String email = "kanjianto@gmail.com";

        //then
        assertThat(underTest.lookForEmail(email)).isNull();
    }
}
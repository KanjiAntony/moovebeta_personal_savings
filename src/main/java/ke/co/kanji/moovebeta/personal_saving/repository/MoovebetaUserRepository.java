package ke.co.kanji.moovebeta.personal_saving.repository;

import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoovebetaUserRepository extends JpaRepository<MoovebetaUser,Long> {
    Optional<MoovebetaUser> findByEmail(String email);

    @Query("SELECT m FROM MoovebetaUser m WHERE m.email=?1")
    MoovebetaUser lookForEmail(String email);
}

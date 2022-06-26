package ke.co.kanji.moovebeta.personal_saving.repository;

import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaSaving;
import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoovebetaSavingRepository extends JpaRepository<MoovebetaSaving,Long> {

    List<MoovebetaSaving> findAllByAccountOwner(MoovebetaUser moovebetaUser);

    MoovebetaSaving findMoovebetaSavingById(Long id);
}

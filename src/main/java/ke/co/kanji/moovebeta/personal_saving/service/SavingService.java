package ke.co.kanji.moovebeta.personal_saving.service;

import ke.co.kanji.moovebeta.personal_saving.dto.SavingRequest;
import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaSaving;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface SavingService {

    void saveAllPending(List<SavingRequest> savingReq, HttpSession session, HttpServletRequest request);
    List<MoovebetaSaving> persistedAccountSavings(HttpServletRequest request);

    MoovebetaSaving searchById(Long id);

    void updateAccountSaving(Long id,MoovebetaSaving savingRequest);
    void deleteAccountSaving(Long id);
}

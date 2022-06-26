package ke.co.kanji.moovebeta.personal_saving.service;

import com.google.common.base.Strings;
import ke.co.kanji.moovebeta.personal_saving.dto.SavingRequest;
import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaSaving;
import ke.co.kanji.moovebeta.personal_saving.repository.MoovebetaSavingRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SavingServiceImpl  implements SavingService{

    private final MoovebetaSavingRepository moovebetaSavingRepository;
    private final RegistrationService registrationService;

    public SavingServiceImpl(MoovebetaSavingRepository moovebetaSavingRepository, RegistrationService registrationService) {
        this.moovebetaSavingRepository = moovebetaSavingRepository;
        this.registrationService = registrationService;
    }

    @Override
    public void saveAllPending(List<SavingRequest> account_savings,
                               HttpSession session, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        List<MoovebetaSaving> all_pending_acc_savings = new ArrayList<>();

        for (SavingRequest acc:account_savings) {
            String bank_name = acc.getBankName();
            String account_name = acc.getAccountName();
            Long account_number = acc.getAccountNumber();
            double amount_saved = acc.getAmountSaved();

            MoovebetaSaving moovebetaSaving = new MoovebetaSaving(
                    bank_name,
                    account_name,
                    account_number,
                    amount_saved,
                    registrationService.lookForEmail(principal.getName())
            );

            all_pending_acc_savings.add(moovebetaSaving);
        }

        moovebetaSavingRepository.saveAll(all_pending_acc_savings);

    }

    @Override
    public List<MoovebetaSaving> persistedAccountSavings(HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        return moovebetaSavingRepository.findAllByAccountOwner(registrationService.lookForEmail(principal.getName()));
    }

    @Override
    public MoovebetaSaving searchById(Long id) {
        return moovebetaSavingRepository.findMoovebetaSavingById(id);
    }

    @Override
    public void updateAccountSaving(Long id,MoovebetaSaving savingRequest) {
        Optional<MoovebetaSaving> existingAccountSaving = moovebetaSavingRepository.findById(id);

        if(existingAccountSaving.isPresent()) {
            MoovebetaSaving eAccountSaving = existingAccountSaving.get();
            eAccountSaving.setId(id);
            eAccountSaving.setBankName(savingRequest.getBankName());
            eAccountSaving.setAccountName(savingRequest.getAccountName());
            eAccountSaving.setAccountNumber(savingRequest.getAccountNumber());
            eAccountSaving.setAmountSaved(savingRequest.getAmountSaved());

            moovebetaSavingRepository.save(eAccountSaving);
        }
    }

    @Override
    public void deleteAccountSaving(Long id) {
        MoovebetaSaving moovebetaSaving = moovebetaSavingRepository.getById(id);

        if(!Strings.isNullOrEmpty(moovebetaSaving.getId().toString())) {
            moovebetaSavingRepository.deleteById(id);
        }
    }
}

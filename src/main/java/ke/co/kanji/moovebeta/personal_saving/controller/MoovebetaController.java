package ke.co.kanji.moovebeta.personal_saving.controller;

import cn.apiclub.captcha.Captcha;
import ke.co.kanji.moovebeta.personal_saving.dto.RegistrationRequest;
import ke.co.kanji.moovebeta.personal_saving.dto.SavingRequest;
import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaSaving;
import ke.co.kanji.moovebeta.personal_saving.entity.MoovebetaUser;
import ke.co.kanji.moovebeta.personal_saving.repository.MoovebetaSavingRepository;
import ke.co.kanji.moovebeta.personal_saving.service.RegistrationService;
import ke.co.kanji.moovebeta.personal_saving.service.SavingService;
import ke.co.kanji.moovebeta.personal_saving.service.UserSecurityService;
import ke.co.kanji.moovebeta.personal_saving.utils.CaptchaUtil;
import ke.co.kanji.moovebeta.personal_saving.utils.MoovebetaUserValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MoovebetaController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private MoovebetaUserValidationUtil validationUtil;

    @Autowired
    private SavingService savingService;

    public void captchaOps(RegistrationRequest registrationRequest) {

        Captcha captcha = CaptchaUtil.createCaptcha(200,60);
        registrationRequest.setTypedCaptcha("");
        registrationRequest.setAnswerCaptcha(captcha.getAnswer());
        registrationRequest.setActualCaptcha(CaptchaUtil.encodeCaptcha(captcha));

    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if(userSecurityService.isAuthenticated()) {
            return "redirect:/welcome";
        }

        RegistrationRequest regReq = new RegistrationRequest();
        captchaOps(regReq);
        model.addAttribute("registrationForm", regReq);

        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("registrationForm") RegistrationRequest registrationRequest,
                               BindingResult bindingResult, Model model) {

        MoovebetaUser user = new MoovebetaUser(
                registrationRequest.fullname,
                registrationRequest.email,
                registrationRequest.phoneNumber,
                registrationRequest.password,
                registrationRequest.role,
                LocalDateTime.now()
        );

        if(registrationRequest.getTypedCaptcha().equals(registrationRequest.getAnswerCaptcha())) {

            validationUtil.validate(user, bindingResult);

            if (bindingResult.hasErrors()) {
                return "registration";
            }

            registrationService.register(registrationRequest);

            userSecurityService.autoLogin(registrationRequest.email, registrationRequest.password);

            return "redirect:/welcome";

        } else {
            RegistrationRequest regReq = new RegistrationRequest();
            captchaOps(regReq);
            model.addAttribute("registrationForm", regReq);
            model.addAttribute("captcha_error","Captcha entered is invalid");
        }

        return "registration";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (userSecurityService.isAuthenticated()) {
            return "redirect:/";
        }

        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @PostMapping("/session/savings")
    public String sessionSavings(@ModelAttribute("savingsForm") SavingRequest savingRequest, HttpServletRequest request) {

        SavingRequest saving_req = new SavingRequest(
                savingRequest.getBankName(),
                savingRequest.getAccountName(),
                savingRequest.getAccountNumber(),
                savingRequest.getAmountSaved()
        );


        List<SavingRequest> account_savings = (List<SavingRequest>) request.getSession().getAttribute("ACCOUNT_SAVINGS");

        if (account_savings == null) {
            account_savings = new ArrayList<>();

            request.getSession().setAttribute("ACCOUNT_SAVINGS", account_savings);
        }
        account_savings.add(saving_req);
        request.getSession().setAttribute("ACCOUNT_SAVINGS", account_savings);

        return "redirect:/welcome";
    }

    @PostMapping("/signout")
    public String signout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/welcome";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model, HttpSession session,HttpServletRequest request) {

        model.addAttribute("savingsForm",new SavingRequest());

        List<MoovebetaSaving> persisted_account_savings = savingService.persistedAccountSavings(request);

        List<SavingRequest> account_savings = (List<SavingRequest>) session.getAttribute("ACCOUNT_SAVINGS");
        model.addAttribute("accountSavings", account_savings!=null? account_savings:new ArrayList<>());
        model.addAttribute("persistedAccountSavings", persisted_account_savings!=null? persisted_account_savings:new ArrayList<>());
        return "welcome";
    }

    @PostMapping("/session/addSavings")
    public String addSavings(Model model, HttpSession session,HttpServletRequest request) {

        model.addAttribute("savingsForm",new SavingRequest());

        List<SavingRequest> account_savings = (List<SavingRequest>) session.getAttribute("ACCOUNT_SAVINGS");

        savingService.saveAllPending(account_savings,session,request);

        request.getSession().invalidate();

        List<MoovebetaSaving> persisted_account_savings = savingService.persistedAccountSavings(request);

        model.addAttribute("persistedAccountSavings", persisted_account_savings!=null? persisted_account_savings:new ArrayList<>());

        return "welcome";
    }

    @GetMapping("/edit/{id}")
    public String showEditSaving(@PathVariable("id") long id, Model model) {
        MoovebetaSaving editSavings = savingService.searchById(id);
        model.addAttribute("editSavingsForm",editSavings);
        return "update_savings";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid MoovebetaSaving moovebetaSaving,
                              Model model) {
        savingService.updateAccountSaving(id,moovebetaSaving);

        return "redirect:/welcome";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        savingService.deleteAccountSaving(id);
        return "redirect:/welcome";
    }

}

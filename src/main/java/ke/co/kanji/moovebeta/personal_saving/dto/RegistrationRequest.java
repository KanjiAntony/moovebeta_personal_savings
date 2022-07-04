package ke.co.kanji.moovebeta.personal_saving.dto;

import ke.co.kanji.moovebeta.personal_saving.security.roles_and_permissions.UserRoles;
import lombok.Data;
import lombok.Getter;


@Data
@Getter
public class RegistrationRequest {
    public String fullname;
    public String email;
    public int phoneNumber;
    public UserRoles role;
    public String password;

    public String typedCaptcha;
    public String answerCaptcha;
    public String actualCaptcha;

    public RegistrationRequest(String fullname, String email, int phoneNumber, UserRoles role, String password) {
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.password = password;
    }

    public RegistrationRequest(String fullname, String email, int phoneNumber, UserRoles role, String password, String typedCaptcha, String answerCaptcha, String actualCaptcha) {
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.password = password;
        this.typedCaptcha = typedCaptcha;
        this.answerCaptcha = answerCaptcha;
        this.actualCaptcha = actualCaptcha;
    }

    public RegistrationRequest() {
    }
}

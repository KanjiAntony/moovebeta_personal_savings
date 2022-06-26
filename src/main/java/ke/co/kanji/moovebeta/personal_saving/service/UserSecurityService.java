package ke.co.kanji.moovebeta.personal_saving.service;

public interface UserSecurityService {
    boolean isAuthenticated();
    void autoLogin(String email, String password);
}

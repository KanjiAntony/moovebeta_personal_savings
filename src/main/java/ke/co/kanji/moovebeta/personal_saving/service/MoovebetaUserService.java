package ke.co.kanji.moovebeta.personal_saving.service;

import ke.co.kanji.moovebeta.personal_saving.repository.MoovebetaUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MoovebetaUserService implements UserDetailsService {

    private final MoovebetaUserRepository repository;

    public MoovebetaUserService(MoovebetaUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with the email %s is not found",username)));
    }
}

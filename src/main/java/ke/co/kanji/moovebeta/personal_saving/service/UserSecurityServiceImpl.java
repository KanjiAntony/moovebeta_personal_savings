package ke.co.kanji.moovebeta.personal_saving.service;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityServiceImpl implements UserSecurityService{

    private final AuthenticationManager authenticationManager;
    private final MoovebetaUserService moovebetaUserService;

    public UserSecurityServiceImpl(AuthenticationManager authenticationManager, MoovebetaUserService moovebetaUserService) {
        this.authenticationManager = authenticationManager;
        this.moovebetaUserService = moovebetaUserService;
    }

    @Override
    public boolean isAuthenticated() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())){
            return false;
        }

        return authentication.isAuthenticated();

    }

    @Override
    public void autoLogin(String email, String password) {

        UserDetails userDetails = moovebetaUserService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if(usernamePasswordAuthenticationToken.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

    }
}

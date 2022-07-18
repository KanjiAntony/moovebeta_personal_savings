package ke.co.kanji.moovebeta.personal_saving.entity;

import ke.co.kanji.moovebeta.personal_saving.security.roles_and_permissions.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class MoovebetaUser implements UserDetails {

    @Id
    @SequenceGenerator(
            name = "moovebeta_user_sequence",
            sequenceName = "moovebeta_user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "moovebeta_user_sequence"
    )
    private Long id;

    @NotNull(message = "Name should not be empty")
    @Column(nullable = false)
    private String full_name;

    @Column(nullable = false)
    private String email;

    @NotNull(message = "Phone number should not be empty")
    @Column(nullable = false)
    private int phone_number;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserRoles roles;

    private boolean isAccountNonLocked = true;
    private boolean isEnabled = true;

    @Column(nullable = false)
    private static LocalDateTime registered_on;

    public MoovebetaUser(String full_name,
                         String email,
                         int phone_number,
                         String password,
                         UserRoles roles) {
        this.full_name = full_name;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
        this.roles = roles;
        registered_on = LocalDateTime.now();
    }

    public MoovebetaUser(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.getAuthority();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getFullname() {
        return full_name;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public int getPhoneNumber() {
        return phone_number;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public LocalDateTime getRegisteredOn(){
        return registered_on;
    }

    @Override
    public String toString() {
        return "MoovebetaUser{" +
                "id=" + id +
                ", full_name='" + full_name + '\'' +
                ", email='" + email + '\'' +
                ", phone_number=" + phone_number +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isEnabled=" + isEnabled +
                ", registered_on=" + registered_on +
                '}';
    }
}

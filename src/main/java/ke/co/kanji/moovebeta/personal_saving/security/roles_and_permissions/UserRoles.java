package ke.co.kanji.moovebeta.personal_saving.security.roles_and_permissions;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ke.co.kanji.moovebeta.personal_saving.security.roles_and_permissions.UserPermissions.*;
import static ke.co.kanji.moovebeta.personal_saving.security.roles_and_permissions.UserPermissions.BLOCK_USERS;

public enum UserRoles {

    ADMIN(Sets.newHashSet(BLOCK_USERS)),
    USER(Sets.newHashSet(SAVING_CRUD));

    Set<UserPermissions> userPermissions;

    UserRoles(Set<UserPermissions> permissions) {
        this.userPermissions = permissions;
    }

    public Set<UserPermissions> getUserPermissions() {
        return this.userPermissions;
    }

    public Set<SimpleGrantedAuthority> getAuthority() {

        Set<SimpleGrantedAuthority> grantedAuthorities = getUserPermissions()
                .stream()
                .map(perm -> new SimpleGrantedAuthority(perm.getPermissions()))
                .collect(Collectors.toSet());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));

        return grantedAuthorities;
    }
}

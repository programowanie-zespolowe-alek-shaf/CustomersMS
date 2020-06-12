package pl.agh.customers.application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
final class WithUserDetailsSecurityContextFactory implements WithSecurityContextFactory<WithCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomUser withUser) {
        String username = StringUtils.hasLength(withUser.username()) ? withUser
                .username() : withUser.value();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (String role : withUser.roles()) {
            if (role.startsWith("ROLE_")) {
                throw new IllegalArgumentException("roles cannot start with ROLE_ Got "
                        + role);
            }
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, withUser.password(), grantedAuthorities);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
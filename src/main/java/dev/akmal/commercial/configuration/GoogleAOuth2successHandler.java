package dev.akmal.commercial.configuration;

import dev.akmal.commercial.model.User;
import dev.akmal.commercial.repository.RoleRepository;
import dev.akmal.commercial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleAOuth2successHandler implements AuthenticationSuccessHandler {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Qualifier("httpServletRequest")
    @Autowired
    private ServletRequest httpServletRequest;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String email = token.getPrincipal().getAttributes().get("email").toString();

        if (!userRepository.findUserByEmail(email).isPresent()) {

        }else{
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttributes().get("Given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("last_name").toString());
            user.setEmail(email);
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).get());
            user.setRoles(roles);
            userRepository.save(user);

        }

        redirectStrategy.sendRedirect((HttpServletRequest) httpServletRequest, (HttpServletResponse) httpServletRequest, "/");
    }
}

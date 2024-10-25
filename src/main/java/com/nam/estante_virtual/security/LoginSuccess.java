package com.nam.estante_virtual.security;

import java.io.IOException;

import com.nam.estante_virtual.model.Role;
import com.nam.estante_virtual.model.User;
import com.nam.estante_virtual.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class LoginSuccess extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        String login = authentication.getName();

        User user = userRepository.findByLogin(login);

        String redirectURL = "";
        if (hasAuthorization(user, "ADMIN")) {
            redirectURL = "/auth/admin/admin-index";
        } else if (hasAuthorization(user, "USER")) {
            redirectURL = "/auth/user/user-index";
        } else if (hasAuthorization(user, "BIBLIOTECARIO")) {
            redirectURL = "/auth/librarian/librarian-index";
        }
        response.sendRedirect(redirectURL);
    }

    private boolean hasAuthorization(User user, String role) {
        for(Role ff: user.getRoles()) {
            if (ff.getRole().equals(role)) {
                return true;
            }
        }
        return false;
    }


}

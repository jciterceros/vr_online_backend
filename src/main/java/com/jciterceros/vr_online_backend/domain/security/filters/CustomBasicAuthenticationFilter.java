package com.jciterceros.vr_online_backend.domain.security.filters;

import com.jciterceros.vr_online_backend.domain.security.models.User;
import com.jciterceros.vr_online_backend.domain.security.models.UserPrincipal;
import com.jciterceros.vr_online_backend.domain.security.repositories.UserRepository;
import com.jciterceros.vr_online_backend.domain.security.services.IPasswordEncoder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
//@RequiredArgsConstructor
public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";

    private final UserRepository userRepository;

    private final IPasswordEncoder passwordEncoder;

    @Autowired
    public CustomBasicAuthenticationFilter(UserRepository userRepository, IPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isBasicAuthentication(request)) {
            String[] credentials = decodeBase64(getHeader(request).replace(BASIC, "")).split(":");
            String username = credentials[0];
            String password = credentials[1];

            User user = userRepository.findByUsernameFetchRoles(username);
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("User not found");
                return;
            }
            boolean valid = checkPassword(user.getPassword(), password);
            if (!valid) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid password");
                return;
            }
            setAuthentication(user);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(User user) {
        Authentication authentication = createAuthenticationToken(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication createAuthenticationToken(User user) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    private boolean checkPassword(String userPassword, String loginPassword) {
        return passwordEncoder.matches(loginPassword, userPassword);
    }

    private String decodeBase64(String base64Credentials) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        return new String(decodedBytes);
    }

    private boolean isBasicAuthentication(HttpServletRequest request) {
        String header = getHeader(request);
        return header != null && header.startsWith(BASIC);
    }

    private String getHeader(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION);
    }
}

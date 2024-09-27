package ro.ps.showmgmtbackend.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ro.ps.showmgmtbackend.dto.authentication.LoginRequestDTO;
import ro.ps.showmgmtbackend.exception.ExceptionBody;
import ro.ps.showmgmtbackend.security.util.JwtUtil;
import ro.ps.showmgmtbackend.security.util.SecurityConstants;

import java.io.IOException;

/**
 * Filter class that handles login
 * This includes:
 * - Attempting authentication
 * - Handling successful authentication
 * - Handling unsuccessful authentication
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public LoginFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        this.objectMapper = objectMapper;
        this.setAuthenticationManager(authenticationManager);
        this.setFilterProcessesUrl(SecurityConstants.LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            LoginRequestDTO authenticationRequest = objectMapper.readValue(
                    request.getInputStream(),
                    LoginRequestDTO.class
            );
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            );

            return super.getAuthenticationManager().authenticate(authentication);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String accessToken = JwtUtil.generateJwtToken(
                ((User) authResult.getPrincipal()).getUsername(),
                authResult.getAuthorities()
        );
        response.addCookie(JwtUtil.buildCookie(SecurityConstants.JWT_TOKEN, accessToken));

        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        objectMapper.writeValue(response.getWriter(), new ExceptionBody(failed.getMessage()));
    }
}
package ro.ps.showmgmtbackend.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ro.ps.showmgmtbackend.exception.ExceptionCode;
import ro.ps.showmgmtbackend.model.UserEntity;
import ro.ps.showmgmtbackend.repository.UserRepository;

/**
 * Service class that implements UserDetailsService
 * This includes:
 * - Load user by username
 */
@RequiredArgsConstructor
public class UserDetailsServiceBean implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(this::getUserDetails)
                .orElseThrow(() -> new BadCredentialsException(ExceptionCode.ERR099_INVALID_CREDENTIALS.getMessage()));
    }

    private UserDetails getUserDetails(UserEntity user) {
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}

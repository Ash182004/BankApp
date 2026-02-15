package com.Bank.security;

import com.Bank.entity.User;
import com.Bank.repo.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // ✅ used during login
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.isEnabled()
        );
    }

    // ✅ ADD THIS METHOD (for JWT)
    public CustomUserDetails loadUserById(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with id: " + id));

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole(),
                user.isEnabled()
        );
    }
}
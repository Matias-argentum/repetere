package com.mat.repetere.security;

import com.mat.repetere.model.User;
import com.mat.repetere.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found"));
        CustomUserDetails customUser = new CustomUserDetails();
        customUser.setId(user.getId());
        customUser.setName(user.getName());
        customUser.setEmail(user.getEmail());
        customUser.setNativeLanguage(user.getNativeLanguage());
        customUser.setRole(user.getRole());
        customUser.setPassword(user.getPassword());
        customUser.setActive(user.getActive());
        return customUser;
    }


}

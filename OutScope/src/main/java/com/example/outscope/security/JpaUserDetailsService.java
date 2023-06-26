package com.example.outscope.security;

import com.example.outscope.entity.User;
import com.example.outscope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByAccount(username).orElseThrow(
                () -> new UsernameNotFoundException("Invalid authentication(인증이 잘못되었습니다)")
        );
        return new CustomUserDetails(user);
    }

}

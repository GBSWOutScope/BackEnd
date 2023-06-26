package com.example.outscope.service;

import com.example.outscope.entity.Authority;
import com.example.outscope.entity.User;
import com.example.outscope.entity.dto.SignRequest;
import com.example.outscope.entity.dto.SignResponse;
import com.example.outscope.repository.UserRepository;
import com.example.outscope.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SignService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public SignResponse login(SignRequest request) throws Exception {
        User user = userRepository.findByAccount(request.getAccount())
                .orElseThrow(() -> new BadCredentialsException("잘못된 계정정보 입니다."));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("잘못된 계정정보입니다.");
        }

        return SignResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .token(jwtProvider.createToken(user.getAccount(), user.getRoles()))
                .build();
    }

    public boolean register(SignRequest request) throws Exception {
        try {
            User user = User.builder()
                    .account(request.getAccount())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .classNumber(request.getClassNumber())
                    .job(request.getJob())
                    .build();

            if(user.getJob().equals("학생")) {
                user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));
                userRepository.save(user);
            } else if(user.getJob().equals("선생님")) {
                user.setRoles(Collections.singletonList(Authority.builder().name("ROLE_ADMIN").build()));
                userRepository.save(user);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }

    public List<User> userList() throws Exception {
        List<User> user = userRepository.findAll();
        return user;
    }

    public SignResponse getUser(String account) throws Exception {
        User user = userRepository.findByAccount(account)
                .orElseThrow();
        return new SignResponse(user);
    }

}

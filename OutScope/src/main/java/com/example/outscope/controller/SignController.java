package com.example.outscope.controller;

import com.example.outscope.entity.User;
import com.example.outscope.entity.dto.SignRequest;
import com.example.outscope.entity.dto.SignResponse;
import com.example.outscope.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SignController {

    private final SignService signService;

    @PostMapping(value = "/api/login")
    public ResponseEntity<SignResponse> signing(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(signService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "/api/register")
    public ResponseEntity<Boolean> signup(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(signService.register(request), HttpStatus.OK);
    }

    @GetMapping(value = "/api/user/get")
    public ResponseEntity<SignResponse> getUser(@RequestParam String account) throws Exception {
        return new ResponseEntity<>(signService.getUser(account), HttpStatus.OK);
    }

    @GetMapping("/api/admin/get")
    public ResponseEntity<SignResponse> getUserAdmin(@RequestParam String account) throws Exception {
        return new ResponseEntity<>(signService.getUser(account), HttpStatus.OK);
    }

    @GetMapping("/api/list")
    public ResponseEntity<List<User>> getUserList() throws Exception {
        return new ResponseEntity<>(signService.userList(), HttpStatus.OK);
    }

}

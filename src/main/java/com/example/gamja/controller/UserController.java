package com.example.gamja.controller;

import com.example.gamja.dto.UserSignUpRequestDto;
import com.example.gamja.entity.User;
import com.example.gamja.repository.UserRepository;
import com.example.gamja.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequestDto requestDto, HttpServletResponse response) {
        userService.SignUp(requestDto, response);
        return ResponseEntity.ok("회원가입 성공~!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("회원 삭제 성공!");
    }

}

package com.example.gamja.service;

import com.example.gamja.dto.UserSignUpRequestDto;
import com.example.gamja.entity.User;
import com.example.gamja.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void SignUp(UserSignUpRequestDto UserSignUp, HttpServletResponse response) {
        if(userRepository.findByEmail(UserSignUp.getEmail()).isPresent()){
            throw new RuntimeException("사용자의 이메일이 이미 존재합니다.");
        }
        if(userRepository.findByName(UserSignUp.getName()).isPresent()){
            throw new RuntimeException("이미 존재하는 이메일주소입니다.");
        }
        User user = UserSignUp.toSignUpUser();
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 id를 가진 사용자가 없습니다."));
    }

    // id 값으로 유저 삭제
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("해당 id를 가진 사용자가 없습니다.");
        }
        userRepository.deleteById(id);
    }
}
